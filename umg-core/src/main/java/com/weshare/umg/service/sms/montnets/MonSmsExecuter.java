package com.weshare.umg.service.sms.montnets;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weshare.base.CloseableUtils;
import com.weshare.network.HttpClientUtils;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.entity.SmsSendDetails;
import com.weshare.umg.entity.SmsSpReport;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.response.BalanceResponse;
import com.weshare.umg.service.sms.SmsExecuter;
import com.weshare.umg.system.Operation;
import com.weshare.umg.util.CodeEnum;
import com.weshare.umg.util.UrlConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 17:37
 * @Description:
 */
public class MonSmsExecuter implements SmsExecuter {
    private static final Logger logger = LoggerFactory.getLogger(MonSmsExecuter.class);

    private SmsConfig smsConfig;

    private MonUrlManager monUrlManager;

    public MonSmsExecuter(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    @Override
    public List<SmsSendDetails> sendSms(SmsRequestBo smsRequestBo) throws UmgException{
        Assert.notNull(smsRequestBo,"smsRequestBo can't empty");
        HttpPost post = buildSendHttpPost(smsRequestBo);
        String res = doPost(post);
        validaResult(res);
        return buildSmsSendResp(res,smsRequestBo);
    }

    @Override
    public BalanceResponse getBalance() throws UmgException {
        String[] paths = smsConfig.getMonPaths().split(",");
        Set<String> set = new HashSet<>();
        String address = monUrlManager.getAvailableAddress();
        if(address != null){
            set.add(address);
        }
        set.addAll(new HashSet(Arrays.asList(paths)));
        String body = MonRequestUtils.buildGetBalanceRequestBody(smsConfig);
        String res = null;
        for (String path : set){
            if(path.endsWith("/")) path = path.substring(0,path.length() -1);
            String url = path + UrlConstant.MON_GET_BALANCE_PATH;
            try {
                HttpPost request = HttpClientUtils.getHttpPost(url,null,body,HttpClientUtils.charset);
                res = doPost(request);
                validaResult(res);
                monUrlManager.setAvailableAddress(path);
                break;
            }catch (Exception e){
                logger.error("getBalance error!url={}",url,e);
            }
        }

        BalanceResponse response = countBalance(res);
        return response;
    }



    @Override
    public List<SmsSpReport> getReport() throws UmgException {
        HttpPost post = buildReportHttpPost();
        String res = doPost(post);
        validaResult(res);

        JSONObject jsonObject = JSON.parseObject(res);
        JSONArray array = jsonObject.getJSONArray("rpts");
        return serializeReport(array);
    }

    @Override
    public List<SmsSpReport> serializeReport(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        JSONArray array = jsonObject.getJSONArray("rpts");
        return serializeReport(array);
    }

    public List<SmsSpReport> serializeReport(JSONArray array) {
        List<SmsSpReport> details = array.stream()
                .map(o->{
                    JSONObject object = (JSONObject) o;
                    SmsSpReport smsReport = new SmsSpReport();
                    smsReport.setResId(object.getString("custid"));
                    smsReport.setMobile(object.getString("mobile"));
                    smsReport.setUserAcceptDate(object.getTimestamp("stime"));
                    int status = object.getInteger("status") == 0 ? 1 : 0;
                    smsReport.setStatus(status);
                    smsReport.setErrmsg(object.getString("errcode"));
                    smsReport.setDesc(object.getString("errdesc"));
                    return smsReport;
                }).collect(Collectors.toList());

        return details;
    }


    private HttpPost buildReportHttpPost() throws UmgException {
        String profix = UrlConstant.MON_REPORT_URL;
        String url = getAvailablePath() + profix;
        String body = MonRequestUtils.buildReportRequestBody(smsConfig);
        HttpPost request = HttpClientUtils.getHttpPost(url,null,body,HttpClientUtils.charset);
        return request;
    }

    private HttpPost buildSendHttpPost(SmsRequestBo smsRequestBo) throws UmgException {
        Operation operation = smsRequestBo.getOperation();

        String profix;
        switch (operation){
            case SINGLE_SEND:
                profix = UrlConstant.MON_SINGLE_URL;
                break;
            case BATCH_SEND:
                profix = UrlConstant.MON_BATCH_URL;
                break;
            default:
                throw new UmgException(CodeEnum.OPERATION_ERROR);
        }

        String url = getAvailablePath() + profix;
        String body = MonRequestUtils.buildMonRequestBody(smsRequestBo,smsConfig);
        HttpPost request = HttpClientUtils.getHttpPost(url,null,body,HttpClientUtils.charset);
        return request;
    }

    public String getAvailablePath()throws UmgException{
        String path = monUrlManager.getAvailableAddress();
        if(path == null){
            getBalance();
            path = monUrlManager.getAvailableAddress();
        }

        if(path == null){
            throw new UmgException(CodeEnum.GATEWAY_EXCEPTION);
        }

        return path;
    }



    private BalanceResponse countBalance(String result)throws UmgException{
        if(StringUtils.isBlank(result)){
            throw new UmgException(CodeEnum.GET_BALANCE_EXCEPTION);
        }
        JSONObject jsonObject = JSON.parseObject(result);
        BalanceResponse response = new BalanceResponse();
        response.setType(jsonObject.getInteger("chargetype"))
                .setMoney(jsonObject.getFloatValue("money"))
                .setNumber(jsonObject.getInteger("balance"));
        return  response;
    }

    public static String doPost(HttpPost request)throws UmgException{
        CloseableHttpResponse httpResponse=null;
        String result = null;
        try {
            HttpClient httpClient = HttpClientUtils.getHttpclient();
            httpResponse = (CloseableHttpResponse)httpClient.execute(request);
            // 若状态码为200，则代表请求成功
            if(httpResponse != null && httpResponse.getStatusLine().getStatusCode()==200) {
                result = HttpClientUtils.entityToString(httpResponse,HttpClientUtils.charset);
            }else {
                logger.error("do post error!res={}",httpResponse.getStatusLine().toString());
                throw new UmgException(CodeEnum.NETWORK_ERROR);
            }
            return result;
        }catch (Exception e) {
            logger.error("do post error!url={}",request.getURI().toString(),e);
            throw new UmgException(CodeEnum.NETWORK_ERROR);
        }finally {
            CloseableUtils.close(httpResponse);
        }
    }

    public static String validaResult(String result)throws UmgException {
        Assert.notNull(result,"result not can blank");
        try{
            JSONObject jsonObject = JSON.parseObject(result);
            int code = jsonObject.getInteger("result");

            if(code != 0){
                MonCodeEnum codeEnum = MonCodeEnum.getCodeEnum(code);
                if(codeEnum == null){
                    codeEnum = MonCodeEnum.MON_EXCEPTION;
                }
                throw new UmgException(CodeEnum.SMS_SP_ERROR.getCode(),codeEnum.getCode() + "-" +codeEnum.getMessage());
            }
            return result;
        }catch (UmgException umge){
            throw umge;
        } catch (Exception e) {
            logger.error("http result error!result={}",result);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }



    public static List<SmsSendDetails> buildSmsSendResp(String result, SmsRequestBo smsRequestBo)throws UmgException {
        Assert.notNull(result,"result not can blank");
        List<SmsSendDetails> list= new ArrayList<>();
        try{
//            JSONObject jsonObject = JSON.parseObject(result);
            List<String> mobiles = smsRequestBo.getMobiles();
            mobiles.forEach(mobile->{
                SmsSendDetails details = new SmsSendDetails();
                details.setMobile(mobile);
                details.setResId(smsRequestBo.getUniqueId());
                list.add(details);
            });
            return list;
        }catch (Exception e) {
            logger.error("mon buildBatchSmsSendResp error!result={}",result);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }


    public void setMonUrlManager(MonUrlManager monUrlManager) {
        this.monUrlManager = monUrlManager;
    }

    public static class Builder{

        private SmsConfig smsConfig;
        private RedisTemplate<String, String> redisTemplate;
        private Builder() {}

        public MonSmsExecuter build(){
            Assert.notNull(smsConfig,"smsConfig can't empty");
            Assert.notNull(smsConfig.getMonUserid(),"MonUserid can't empty");
            Assert.notNull(smsConfig.getMonPaths(),"MonPaths can't empty");
            Assert.notNull(redisTemplate,"redisTemplate can't empty");
            Assert.notNull(smsConfig.getReportNum(),"ReportNum can't empty");

            MonSmsExecuter executer = new MonSmsExecuter(smsConfig);
            executer.setMonUrlManager(new MonUrlManager(redisTemplate));
            return  executer;
        }

        public static Builder create(){
            return new Builder();
        }

        public SmsConfig getSmsConfig() {
            return smsConfig;
        }

        public Builder setSmsConfig(SmsConfig smsConfig) {
            this.smsConfig = smsConfig;
            return this;
        }

        public RedisTemplate<String, String> getRedisTemplate() {
            return redisTemplate;
        }

        public Builder setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
            this.redisTemplate = redisTemplate;
            return this;
        }
    }


}
