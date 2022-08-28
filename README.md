# TurkeyInfoBotSpring

Script in GitHub Actions:
1. Trigger by push into Master
2. Script on remote server
   1. Git pull
   2. Stop and delete all existing containers
   3. Build new image using pulled Dockerfile
   4. Run Docker container

Dockerfile:
1. Copy source code to container
2. Maven clean + package
3. Run command: Java with JAR file + Env vars

Secure variables:
1. PostgreSQL URL to DB
2. DB username
3. DB password
4. Weather API key
5. Water temperature API key
6. Bot token for Telegram API
