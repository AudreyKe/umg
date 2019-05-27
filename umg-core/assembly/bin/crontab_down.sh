#!/usr/bin/env bash

#获取crontab所有任务
crontab -l > crontab_downfile
#删除crontab中umg-monitor任务
sed -i '/umg-monitor.sh/d'  crontab_downfile
crontab crontab_downfile