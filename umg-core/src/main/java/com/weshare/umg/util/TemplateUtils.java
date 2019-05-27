package com.weshare.umg.util;

import com.weshare.regex.RegexUtils;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.SmsRequestBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 18:29
 * @Description:
 */
public class TemplateUtils {
    private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);
    private static final String COMPILE = "\\{[\\w]*\\}";


    /**
     * @param smsRequestBo     将的内容将替换，参数tpl${number}成替换tplparams.get(number)值
     * @return
     */
    public static String fitContent(SmsRequestBo smsRequestBo)throws UmgException {
        return fitContent(smsRequestBo.getParams(),smsRequestBo.getTemplateEntity().getSign(),smsRequestBo.getTemplateEntity().getMsg());
    }

    public static String fitContent(List<String> list,String  msg,String sign)throws UmgException {
        try{
            String content = transformContent(msg,list);
            content = "【" + sign + "】" + content;
            return content;
        }catch (Exception e){
            logger.error("fit tpl Content error!",e);
            throw new UmgException(CodeEnum.SMS_TPL_PARAMS_NOT_MATCHER);
        }
    }

    /**
     * @param content
     * @param list     将的内容将替换，参数content${number}成替换list.get(number)值
     * @return
     */
    private static String transformContent(String content, List<String> list) {
        Pattern pattern = Pattern.compile(COMPILE);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            String e = matcher.group();
            String substring = e.substring(1, e.length()-1);
            Integer index = Integer.parseInt(substring) - 1;
            content = content.replaceAll("\\{"+ substring +"\\}", list.get(index));
        }
        return content;
    }

    /**
     * @param tpl
     * @return
     */
    public static List<String> getTplMatches(String tpl)throws UmgException {
          List<String> list = RegexUtils.getMatches(COMPILE,tpl);
          return list ;
    }




}
