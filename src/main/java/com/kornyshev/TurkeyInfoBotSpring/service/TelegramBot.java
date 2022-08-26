package com.kornyshev.TurkeyInfoBotSpring.service;

import com.kornyshev.TurkeyInfoBotSpring.config.BotConfig;
import com.kornyshev.TurkeyInfoBotSpring.models.MessageData;
import com.kornyshev.TurkeyInfoBotSpring.models.User;
import com.kornyshev.TurkeyInfoBotSpring.models.currencies.CurrencyResponse;
import com.kornyshev.TurkeyInfoBotSpring.models.seatemperature.TemperatureByHour;
import com.kornyshev.TurkeyInfoBotSpring.models.weather.WeatherResponse;
import com.kornyshev.TurkeyInfoBotSpring.repositories.MessageRepository;
import com.kornyshev.TurkeyInfoBotSpring.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private CurrencyController currencyController;
    @Autowired
    private WeatherController weatherController;
    @Autowired
    private SeaTemperatureController seaTemperatureController;

    final BotConfig config;

    static final String HELP_TEXT = """
            This bot will send one message once a day with information about exchange rates (USD, TRY, RUB), weather in Antalya and water temperature. It is now set up to trigger the message to be sent at 10:00.
            
            To get the message (as the first, and at any time of day) - use the command /start, through the menu or just in chat. 
            If for today already formed a message, the bot will take available from the database, if not yet - it will appeal to several services for information, create a message and put in the database.
                        
            Perhaps the functionality of the bot will be expanded.
            
            If you want you can delete your date from DB.
            """;

    static final String MAIN_MESSAGE = """
            <b>Periodical message.</b>
            <b>%s</b>
                        
            <b>Currencies:</b>
            %s
                        
            <b>Weather in %s:</b>
            %s°C
            Humidity: %s%%
            Condition: %s
                        
            <b>Sea Temperature in %s:</b>
            %s°C
            """;

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Start"));
        listOfCommands.add(new BotCommand("/help", "Help"));
        listOfCommands.add(new BotCommand("/delete", "Delete your data from DB"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public String getProcessedMainMessage() {
        final Optional<MessageData> anyMessageForToday = ((List<MessageData>) messageRepository.findAll()).stream()
                .filter(message -> message.getMessageDate().toLocalDateTime().toLocalDate().isEqual(LocalDate.now()))
                .findAny();
        if (anyMessageForToday.isPresent()) {
            final MessageData messageData = anyMessageForToday.get();
            log.info("Message for today was found in DB. Message: {}", messageData);
            return processMessageToString(messageData);
        } else {
            final DecimalFormat decimalFormat = new DecimalFormat("0.00");
            final WeatherResponse weatherData = weatherController.getWeatherData(config.getWeatherApiKey(), config.getMainCity());
            final TemperatureByHour seaTemperatureByHour = seaTemperatureController.getSeaTemperatureData(config.getWaterTemperatureApiKey(),
                    weatherData.getLocation().getLat(), weatherData.getLocation().getLon()).getHours().get(0);
            final CurrencyResponse exchangeRatesUsdBase = currencyController.getExchangeRates("USD", List.of("TRY", "RUB"));
            final CurrencyResponse exchangeRatesTryBase = currencyController.getExchangeRates("TRY", List.of("RUB"));
            final List<String> listOfRates = Stream.concat(exchangeRatesUsdBase.getRates().entrySet().stream()
                                    .map(entry -> exchangeRatesUsdBase.getBase() + "/" + entry.getKey() + " = " + decimalFormat.format(entry.getValue())),
                            exchangeRatesTryBase.getRates().entrySet().stream()
                                    .map(entry -> exchangeRatesTryBase.getBase() + "/" + entry.getKey() + " = " + decimalFormat.format(entry.getValue())))
                    .toList();
            final MessageData messageData = new MessageData();
            messageData.setMessageDate(new Timestamp(System.currentTimeMillis()));
            messageData.setRatesString(String.join("\n", listOfRates));
            messageData.setCity(config.getMainCity());
            messageData.setTemperature(weatherData.getCurrent().getTemp_c());
            messageData.setHumidity(weatherData.getCurrent().getHumidity());
            messageData.setCondition(weatherData.getCurrent().getCondition().getText());
            messageData.setWaterTemperature(seaTemperatureByHour.getWaterTemperature().getMeto());
            log.info("Message for today wasn't found in DB. It was generated from API and saved in DB. Message: {}", messageData);
            messageRepository.save(messageData);
            return processMessageToString(messageData);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/start" -> {
                    registerUser(update.getMessage());
                    sendMessage(chatId, getProcessedMainMessage());
                }
                case "/help" -> sendMessage(chatId, HELP_TEXT);
                case "/delete" -> {
                    userRepository.deleteById(chatId);
                    sendMessage(chatId, "Your data was deleted from DB.");
                    userRepository.findAll().forEach(user -> log.info("User: {}", user));
                }
                default -> sendMessage(chatId, "Sorry, command was not recognized");
            }
        }
    }

    public void sendEverydayMessageToEachUser() {
        userRepository.findAll().forEach(user -> sendMessage(user.getChatId(), getProcessedMainMessage()));
    }

    private void sendMessage(long chatId, String textToSend) {
        log.info("Sending message: {}", textToSend);
        SendMessage message = new SendMessage();
        message.enableHtml(true);
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: {}", e.getMessage());
        }
    }

    private void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            var chat = msg.getChat();
            User user = new User();
            user.setChatId(msg.getChatId());
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("User saved: {}", user);
        }
    }

    private String processMessageToString(MessageData messageData) {
        return String.format(MAIN_MESSAGE,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                messageData.getRatesString(),
                messageData.getCity(),
                messageData.getTemperature(),
                messageData.getHumidity(),
                messageData.getCondition(),
                messageData.getCity(),
                messageData.getWaterTemperature());
    }

}
