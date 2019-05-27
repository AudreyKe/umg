package com.weshare.umg.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 17:48
 * @Description:
 */
public class ChannelUtils {

    //获取sms可用通道
    public static List<Integer> findSmsAvailChannel(int code,int sysCode){
        int num = 1;
        int availNum = sysCode & code;
        List<Integer> list = new ArrayList<>();
        while (availNum >= num){
            if((availNum & num) > 0){
                list.add(num);
            }
            num <<= 1;
        }
     return list;
    }

}
