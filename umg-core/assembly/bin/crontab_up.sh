#!/usr/bin/env bash

#获取crontab所有任务
crontab -l > crontab_upfile
#删除crontab中umg-monitor任务
sed -i '/umg-monitor.sh/d'  crontab_upfile

#重新添加umg-monitor监控任务
pwd=/data/app/umg/umg/bin
app_name=com.weshare.umg.UMGBootstrap
echo "* * * * * sh ${pwd}/umg-monitor.sh ${app_name} ${pwd}" >> crontab_upfile
echo "* * * * * sleep 10; sh ${pwd}/umg-monitor.sh ${app_name} ${pwd}" >> crontab_upfile
echo "* * * * * sleep 20; sh ${pwd}/umg-monitor.sh ${app_name} ${pwd}" >> crontab_upfile
echo "* * * * * sleep 30; sh ${pwd}/umg-monitor.sh ${app_name} ${pwd}" >> crontab_upfile
echo "* * * * * sleep 40; sh ${pwd}/umg-monitor.sh ${app_name} ${pwd}" >> crontab_upfile
echo "* * * * * sleep 50; sh ${pwd}/umg-monitor.sh ${app_name} ${pwd}" >> crontab_upfile
crontab crontab_upfile