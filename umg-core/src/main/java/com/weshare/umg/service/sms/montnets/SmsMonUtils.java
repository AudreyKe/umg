package com.weshare.umg.service.sms.montnets;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/29 19:43
 * @Description:
 */
public class SmsMonUtils {
    /**
     * @description 对密码进行加密
     * @param userid 用户账号
     * @param pwd 用户原始密码
     * @param timestamp 时间戳
     * @return 加密后的密码
     */
    public static String encryptPwd(String userid, String pwd, String timestamp) throws Exception{
        String passwordStr = userid.toUpperCase() + "00000000" + pwd + timestamp;
        // 对密码进行加密
        String encryptPwd = getMD5Str(passwordStr);
        // 返回加密字符串
        return encryptPwd;
    }

    /**
     * @description MD5加密方法
     * @param str
     *        需要加密的字符串
     * @return 返回加密后的字符串
     */
    private static String getMD5Str(String str)throws Exception {
        MessageDigest messageDigest = null;
        // 加密前的准备
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(str.getBytes("UTF-8"));
        byte[] byteArray = messageDigest.digest();
        // 加密后的字符串
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if(Integer.toHexString(0xFF & byteArray[i]).length() == 1){
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            }
            else{
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }
}
