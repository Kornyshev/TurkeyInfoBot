# TurkeyInfoBotSpring

Script in GitHub Actions:
1. Trigger by push into Master
2. Script on remote server
   1. Stop service with JAR file
   2. Navigate to working directory
   3. Maven clean
   4. Git pull
   5. Maven package
   6. Start service with new JAR file

Useful command for service management and so on:
1. systemctl start telegram-bot.service
2. systemctl stop telegram-bot.service
3. systemctl status telegram-bot.service