#coding: utf-8
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.header import Header
import datetime
import sys
import socket
import os


def test_init():
    config = {}
    #test
    config["smtp"]='10.83.0.44'
    config["sender"] = 'um-sit@weshareholdings.com.cn'
    config["receiver"] = ['mudong.xiao@weshareholdings.com']
    return config

def prod_init():
    config = {}
    #prod
    config["smtp"]='10.80.0.133'
    config["sender"] = 'um@service.weshareholdings.com'
    config["receiver"] = ['mudong.xiao@weshareholdings.com','finley.li@weshareholdings.com']
    return config

def build_msg(subject,config):
    subject=Header(subject, 'utf-8').encode()
    msg = MIMEMultipart('mixed')
    msg['Subject'] = subject
    msg['From'] = config["sender"]
    msg['To'] = ";".join(config["receiver"])
    msg['Date']= datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    return msg

def add_text(msg,text):
    text_plain = MIMEText(text, 'plain', 'utf-8')
    msg.attach(text_plain)

def add_html(msg,text):
    mail_html = MIMEText(text, 'html', 'utf-8')
    msg.attach(mail_html)

def add_file(msg,path):
    basename = os.path.basename(path)
    sendfile = open(path, 'rb').read()
    text_att = MIMEText(sendfile, 'base64', 'utf-8')
    text_att["Content-Type"] = 'application/octet-stream'
    text_att.add_header('Content-Disposition', 'attachment', filename=('utf-8', '', basename))
    msg.attach(text_att)

def send_mail(config, msg):
    smtp = smtplib.SMTP()
    smtp.connect(config["smtp"])
    smtp.set_debuglevel(1)
    smtp.sendmail(config["sender"],  config["receiver"], msg.as_string())
    smtp.quit()

def parseArgument():
    argus = {}
    text=""
    for i in range(1,len(sys.argv)):
        text+=sys.argv[i]
    argus["text"] = text
    return argus

def get_host_ip():
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(('8.8.8.8', 80))
        ip = s.getsockname()[0]
    finally:
        s.close()
    return ip

def dis_env(ip):
    iparr = ip.split('.')
    seg = iparr[1]
    #默认生产
    config = prod_init()
    #if 测试
    if seg == '83':
        config = test_init()
    return config

if __name__ == '__main__':
    ip = get_host_ip()
    log_path = "/data/logs/umg/umg_info.txt"
    subject = "umg-监控报警信息"
    text = "服务器ip:"+ip
    config = dis_env(ip)
    msg = build_msg(subject, config)
    add_text(msg,text)
    add_file(msg,log_path)
    send_mail(config,msg)