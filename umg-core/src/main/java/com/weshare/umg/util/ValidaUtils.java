package com.weshare.umg.util;

import com.weshare.base.Constant;
import com.weshare.regex.RegexUtils;
import com.weshare.umg.entity.TemplateEntity;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.MailAcRequest;
import com.weshare.umg.request.sms.SmsRequest;
import com.weshare.umg.system.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/17 19:33
 * @Description:
 */
public class ValidaUtils {
    private static final Logger logger = LoggerFactory.getLogger(ValidaUtils.class);
    private static final String MOBILE = "1[2345789]\\d{9}$";

    public static void validateMailRequest(MailAcRequest request) throws UmgException {

        if(StringUtils.isBlank(request.getAppCode())){
            logger.error("{}!",CodeEnum.APPCODE_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.APPCODE_ISEMPTY);
        }
        //todo wait um
//        if(StringUtils.isBlank(request.getCustId())){
//            logger.error("{}!",CodeEnum.CUSTID_ISEMPTY.getMessage());
//            throw new UmgException(CodeEnum.CUSTID_ISEMPTY);
//        }

        Channel channel = request.getChannel();
        if(!Channel.MIAL_SEND.equals(channel)){
            logger.error("{}!",CodeEnum.CHANNEL_ERROR.getMessage());
            throw new UmgException(CodeEnum.CHANNEL_ERROR);
        }


        Operation operation = request.getOperation();
        if(!Operation.SYSTEM_LOGIC.equals(operation)){
            logger.error("{}!",CodeEnum.OPERATION_ERROR.getMessage());
            throw new UmgException(CodeEnum.OPERATION_ERROR);
        }

        String to = request.getTo();
        if(StringUtils.isBlank(to)){
            logger.error("{}!",CodeEnum.MAIL_TO_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.MAIL_TO_ISEMPTY);
        }
        validateEmail(to);
        String cc = request.getCc();
        if(StringUtils.isNotBlank(cc)){
            validateEmail(cc);
        }
        String bcc = request.getBcc();
        if(StringUtils.isNotBlank(bcc)){
            validateEmail(bcc);
        }


    }

    public static void validateEmail(String mails) throws UmgException {
        String[] emails = mails.split(Constant.SPLIT);
        for(String email : emails){
            if(!RegexUtils.checkEmail(email)){
                logger.error("{}!email={}",CodeEnum.EMAIL_FORMAT_ERROR.getMessage(),email);
                throw new UmgException(CodeEnum.EMAIL_FORMAT_ERROR);
            }
        }
    }



    public static void validateSmsTpl(SmsRequest request, TemplateEntity templateEntity) throws UmgException {
        if(templateEntity == null){
            logger.error("{}!",CodeEnum.SMS_TPL_CODE_ERROR.getMessage());
            throw new UmgException(CodeEnum.SMS_TPL_CODE_ERROR);
        }

        List<String> list = TemplateUtils.getTplMatches(templateEntity.getMsg());
        if(request.getParams().size() != list.size()){
            logger.error("{}!",CodeEnum.SMS_TPL_PARAMS_NOT_MATCHER.getMessage());
            throw new UmgException(CodeEnum.SMS_TPL_PARAMS_NOT_MATCHER);
        }
    }


    public static void validateSmsRequest(SmsRequest request) throws UmgException {
        if(StringUtils.isBlank(request.getCustId())){
            logger.error("{}!",CodeEnum.CUSTID_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.CUSTID_ISEMPTY);
        }
        Channel channel = request.getChannel();
        if(channel == null || !channel.equals(Channel.SMS_SEND)){
            logger.error("{}!",CodeEnum.CHANNEL_ERROR.getMessage());
            throw new UmgException(CodeEnum.CHANNEL_ERROR);
        }

        Operation operation = request.getOperation();
        if(operation == null){
            logger.error("{}!",CodeEnum.OPERATION_ERROR.getMessage());
            throw new UmgException(CodeEnum.OPERATION_ERROR);
        }

        List<String> mobiles = request.getMobiles();
        if(mobiles == null || mobiles.size() == 0){
            logger.error("{}!",CodeEnum.MOBILE_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.MOBILE_ISEMPTY);
        }
        validateMobile(mobiles);

        if(StringUtils.isBlank(request.getTplCode())){
            logger.error("{}!",CodeEnum.SMS_TPL_CODE_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.SMS_TPL_CODE_ISEMPTY);
        }
//模板参数可能为0
//        List<String> params = request.getParams();
//        if(params == null || params.size() == 0){
//            logger.error("{}!",CodeEnum.SMS_TPL_PARAMS_ISEMPTY.getMessage());
//            throw new UmgException(CodeEnum.SMS_TPL_PARAMS_ISEMPTY);
//        }

        String version = request.getVersion();
        if(Version.getVersion(version) == null){
            logger.error("{}!",CodeEnum.VERSION_ERROR.getMessage());
            throw new UmgException(CodeEnum.VERSION_ERROR);
        }

        Nation nation = request.getNation();
        if(nation != null && nation != Nation.CHINA){
            logger.error("{}!",CodeEnum.NATION_ERROR.getMessage());
            throw new UmgException(CodeEnum.NATION_ERROR);
        }

    }

    private static void validateMobile(List<String> mobiles) throws UmgException {
        for(String mobile : mobiles){
            if(!RegexUtils.regular(mobile,MOBILE)){
                logger.error("{}!mobile={}",CodeEnum.MOBILE_FORMAT_ERROR.getMessage(),mobile);
                throw new UmgException(CodeEnum.MOBILE_FORMAT_ERROR);
            }
        }
    }
}
