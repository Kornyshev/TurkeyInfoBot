#!/bin/sh
cd /root/telegram-bot/TurkeyInfoBot/target
java
-DSPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/new_bot_db"
-DSPRING_DATASOURCE_USERNAME="root"
-DSPRING_DATASOURCE_PASSWORD="root"
-DWEATHER_API_KEY="API_KEY"
-DWATER_TEMPERATURE_API_KEY="API_KEY"
-DBOT_TOKEN="BOT_TOKEN"
-DADMIN_ID="ADMIN_CHAT_ID"
-jar TurkeyInfoBot.jar