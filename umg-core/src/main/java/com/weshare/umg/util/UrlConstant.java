package com.weshare.umg.util;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 15:12
 * @Description: sms url
 */
public class UrlConstant {

    // 梦网科技请求路径
    public static final String MON_SINGLE_URL= "/sms/v2/std/single_send";
    public static final String MON_BATCH_URL= "/sms/v2/std/batch_send";
    public static final String MON_REPORT_URL= "/sms/v2/std/get_rpt";
    public static final String MON_GET_BALANCE_PATH = "/sms/v2/std/get_balance";

    public static String TXC_SINGLE_URL = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";
    public static String TXC_BATCH_URL = "https://yun.tim.qq.com/v5/tlssmssvr/sendmultisms2";
    public static String TXC_GET_STATUS_URL = "https://yun.tim.qq.com/v5/tlssmssvr/pullstatus";
    public static String TXC_GET_BALANCE_URL = "https://yun.tim.qq.com/v5/tlssmssvr/getsmspackages";


}
