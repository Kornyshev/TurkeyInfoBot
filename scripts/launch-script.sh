#!/bin/sh
systemctl stop telegram-bot.service
cd /root/telegram-bot/TurkeyInfoBot
mvn clean
git pull
mvn package
systemctl start telegram-bot.service
