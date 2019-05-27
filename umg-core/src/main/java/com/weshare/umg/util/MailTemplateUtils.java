package com.weshare.umg.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MailTemplateUtils {
    private static final String COMPILE = "\\{[\\w]*\\}";

    public static final String BALANCE_WARN = "<p>尊敬的UMG用户，您好：</p> 您在{1}购买的短信服务余额为 {2}</p>";




    public static String transformContent(String content, String ... params) {
        Pattern pattern = Pattern.compile(COMPILE);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            String e = matcher.group();
            String substring = e.substring(1, e.length()-1);
            Integer index = Integer.parseInt(substring) - 1;
            content = content.replaceAll("\\{"+ substring +"\\}", params[index]);
        }
        return content;
    }

}
