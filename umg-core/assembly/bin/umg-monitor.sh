#!/usr/bin/env bash

APP_NAME=$1
pwd=$2

function restart_app(){
        sh ${pwd}/umg-shutdown.sh
        sleep 1
        nohup sh ${pwd}/umg-startup.sh 2>&1 >/dev/null &
}

function print_log(){
    log_path=/data/logs/umg
    echo ">>>>>>>>>>> umg app info <<<<<<<<<<<<<<<" > $log_path/umg_info.txt
    `ps -ef|grep java|grep $APP_NAME|grep -v grep >> $log_path/umg_info.txt`
    `tail -n 250 $log_path/*.log >> $log_path/umg_info.txt`
}
function send_email(){
        #暂停5s 等app启动完毕
        sleep 5
        #打印日志
        print_log
        #发邮件
        python ${pwd}/send_mail.py
}

cron=`crontab -l | grep umg-monitor.sh | grep -v grep`
pid=`ps -ef|grep java|grep $APP_NAME|grep -v grep|awk '{print $2}'`
#应用down掉 并且 监控的定时任务还在
if [ -z "$pid" ] && [ -n "$cron" ]; then
    restart_app
    send_email
fi


