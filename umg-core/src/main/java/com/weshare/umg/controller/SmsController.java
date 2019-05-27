package com.weshare.umg.controller;


import com.weshare.id.UniqueIdUtils;
import com.weshare.json.JsonUtils;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.sms.QuerySmsSendRequest;
import com.weshare.umg.request.SmsAcRequest;
import com.weshare.umg.response.base.Result;
import com.weshare.umg.response.base.ResultUtil;
import com.weshare.umg.response.send.SmsSendApiResponse;
import com.weshare.umg.service.sms.SmsService;
import com.weshare.umg.util.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/sms")
public class SmsController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
    private static final Logger STORE_ERROR_LOG = LoggerFactory.getLogger("store-error");

    @Autowired
    private SmsService smsService;


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public Result send(@RequestBody String requestBody) {
        String uniqueId = UniqueIdUtils.nextId();
        SmsAcRequest smsAcRequest;
        try {
            smsAcRequest = JsonUtils.convertJsonToObject(requestBody, SmsAcRequest.class);
            smsAcRequest.setUniqueId(uniqueId);
            logger.info("receive http sms! uniqueId={},custId={},appcode={}",uniqueId,smsAcRequest.getCustId(),smsAcRequest.getAppCode());

            CodeEnum codeEnum = smsService.acceptHttpSms(smsAcRequest);
            if(!codeEnum.equals(CodeEnum.SUCCESS_CODE)){
                logger.error("send http sms error! msg={},uniqueId={},custId={},appcode={},object={}",codeEnum.getMessage(),uniqueId,smsAcRequest.getCustId(),smsAcRequest.getAppCode(),requestBody);
                throw new UmgException(codeEnum.getCode(),codeEnum.getMessage());
            }
        }catch (Exception e){
            STORE_ERROR_LOG.info("http sms|uniqueId={},body={}",uniqueId,requestBody);
            if(e instanceof UmgException){
                UmgException ue = (UmgException) e;
                return ResultUtil.error(ue.getCode(),ue.getMessage());
            }
            logger.error("send http sms error! uniqueId={},object={}",uniqueId,requestBody,e);
            return ResultUtil.error(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMessage());
        }
        return ResultUtil.success();
    }


    @RequestMapping(value = "/querySendDetails", method = RequestMethod.POST)
    @ResponseBody
    public Result querySendDetails(@RequestBody String requestBody) {
        QuerySmsSendRequest sendRequest = null;
        Result result = ResultUtil.success();
        try {
            sendRequest = JsonUtils.convertJsonToObject(requestBody, QuerySmsSendRequest.class);
            List<SmsSendApiResponse> list = smsService.querySmsSendDetails(sendRequest);
            result.setData(list);
        }catch (UmgException ue){
            logger.error("querySendDetails error! custId={},object={}",sendRequest.getCustId(),requestBody,ue);
            result =  ResultUtil.error(ue.getCode(),ue.getMessage());
        }
        catch (Exception e){
            logger.error("querySendDetails error! custId={},object={}",sendRequest.getCustId(),requestBody,e);
            result =  ResultUtil.error(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/pushHttpReport", method = RequestMethod.GET)
    @ResponseBody
    public Result pushHttpReport() {
        Result result = ResultUtil.success();
        try {
            logger.info("通过接口拉取短信回执报告");
            smsService.pushHttpReport();
        }catch (Exception e){
            logger.info("通过接口拉取短信回执报告失败",e);
            result =  ResultUtil.error(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMessage());
        }
        return result;
    }


}
