# TurkeyInfoBotSpring

Script in GitHub Actions:
1. Trigger by push into Master
2. Script on remote server
   1. Navigate to working directory
   2. Git pull
   3. Maven clean + package
   4. Rerun existing Java service with new JAR file