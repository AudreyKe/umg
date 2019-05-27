package com.weshare.umg.service.sms.txc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weshare.base.CloseableUtils;
import com.weshare.json.JsonUtils;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 17:37
 * @Description:
 */
public class TxcSmsExecuter implements SmsExecuter {
    private static final Logger logger = LoggerFactory.getLogger(TxcSmsExecuter.class);

    private SmsConfig smsConfig;

    public TxcSmsExecuter(SmsConfig smsConfig) {
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
        JSONArray array = new JSONArray();
        for (int i=0;;i++){

            HttpPost httpPost = buildBalanceHttpPost(i);
            String result = doPost(httpPost);
            JSONObject jsonObject = JSON.parseObject(result);
            Integer total = jsonObject.getInteger("total");
            if(total == null || total == 0){
                break;
            }
            JSONArray data = jsonObject.getJSONArray("data");
            array.addAll(data);
        }
        BalanceResponse response = countBalance(array);
        return response;
    }

    @Override
    public List<SmsSpReport> getReport() throws UmgException {
        HttpPost httpPost = buildReportHttpPost();
        String result = doPost(httpPost);
        validaResult(result);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray array = jsonObject.getJSONArray("data");
        return serializeReport(array);
    }

    @Override
    public List<SmsSpReport> serializeReport(String body){
        JSONArray array = JSON.parseArray(body);
        return serializeReport(array);

    }

    private List<SmsSpReport> serializeReport(JSONArray array){
        List<SmsSpReport> details = array.stream()
                .map(o->{
                    JSONObject object = (JSONObject) o;
                    SmsSpReport smsReport = new SmsSpReport();
                    smsReport.setResId(object.getString("sid"));
                    smsReport.setMobile(object.getString("mobile"));
                    smsReport.setUserAcceptDate(object.getTimestamp("user_receive_time"));
                    int status = object.getString("report_status").equals("SUCCESS") ? 1 : 0;
                    smsReport.setStatus(status);
                    smsReport.setErrmsg(object.getString("errmsg"));
                    smsReport.setDesc(object.getString("description"));
                    return smsReport;
                }).collect(Collectors.toList());
        return details;
    }



    private HttpPost buildSendHttpPost(SmsRequestBo smsRequestBo) throws UmgException {
        Operation operation = smsRequestBo.getOperation();
        switch (operation){
            case SINGLE_SEND:
                return buildSingle(smsRequestBo);
            case BATCH_SEND:
                return buildBatch(smsRequestBo);
            default:
                throw new UmgException(CodeEnum.OPERATION_ERROR);
        }
    }

    private HttpPost buildSingle(SmsRequestBo smsRequestBo) {
        String url = UrlConstant.TXC_SINGLE_URL;
        Map<String, String> querys = buildQuerys();
        String body = TxcRequestUtils.buildSingleRequestBody(smsRequestBo,smsConfig,querys);
        HttpPost request = HttpClientUtils.getHttpPost(url,querys,body,HttpClientUtils.charset);
        return request;
    }


    private HttpPost buildBatch(SmsRequestBo smsRequestBo) {
        String url = UrlConstant.TXC_BATCH_URL;
        Map<String, String> querys = buildQuerys();
        String body = TxcRequestUtils.buildBatchRequestBody(smsRequestBo,smsConfig,querys);
        HttpPost request = HttpClientUtils.getHttpPost(url,querys,body,HttpClientUtils.charset);
        return request;
    }


    private BalanceResponse countBalance(JSONArray array){
        BalanceResponse response = new BalanceResponse();
        response.setType(0).setMoney((float) 0);
        Date now = new Date();
        Integer amount = 0;
        Integer used = 0;
        for ( Object o : array){
            JSONObject object = (JSONObject) o;
            Date toTime = object.getDate("to_time");
            Date fromTime = object.getDate("from_time");
            if(now.before(fromTime) || toTime.before(now)){
                continue;
            }
            amount += object.getIntValue("amount");
            used += object.getIntValue("used");
        }
        response.setNumber(amount - used);
        return response;
    }



    private HttpPost buildBalanceHttpPost(Integer index)  {
        Map<String, String> querys = buildQuerys();
        String url = UrlConstant.TXC_GET_BALANCE_URL;
        String body = TxcRequestUtils.buildBalanceRequestBody(smsConfig,querys,index);
        HttpPost request = HttpClientUtils.getHttpPost(url,querys,body,HttpClientUtils.charset);
        return request;
    }


    private HttpPost buildReportHttpPost()  {
        Map<String, String> querys = buildQuerys();
        String url = UrlConstant.TXC_GET_STATUS_URL;
        String body = TxcRequestUtils.buildReportRequestBody(smsConfig,querys);
        HttpPost request = HttpClientUtils.getHttpPost(url,querys,body,HttpClientUtils.charset);
        return request;
    }


    public Map<String, String> buildQuerys() {
        Map<String, String> querys = new HashMap<>();
        long random = SmsTxcUtils.getRandom();
        querys.put("sdkappid",String.valueOf(smsConfig.getTxcAppid()));
        querys.put("random",String.valueOf(random));
        return querys;
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
                logger.error("do post httpResponse error!res={},httpResponse",result,httpResponse.toString());
                throw new UmgException(CodeEnum.NETWORK_ERROR);
            }
            return result;
        }catch (Exception e) {
            logger.error("do post error!request={},result={},request={}",request.toString()+request.getEntity().toString(),result,httpResponse.toString(),e);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
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
                String errmsg = code + "-" +jsonObject.getString("errmsg");
                throw new UmgException(CodeEnum.SMS_SP_ERROR.getCode(),errmsg);
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
        Operation operation = smsRequestBo.getOperation();
        switch (operation){
            case SINGLE_SEND:
                return buildSingleSmsSendResp(result,smsRequestBo.getMobiles().get(0));
            case BATCH_SEND:
                return buildBatchSmsSendResp(result);
            default:
                throw new UmgException(CodeEnum.OPERATION_ERROR);
        }
    }

    public static List<SmsSendDetails> buildSingleSmsSendResp(String result, String mobile)throws UmgException {
        Assert.notNull(result,"result not can blank");
        List<SmsSendDetails> list= new ArrayList<>();
        try{
            JSONObject jsonObject = JSON.parseObject(result);
            SmsSendDetails details = new SmsSendDetails();
            details.setMobile(mobile);
            details.setResId(jsonObject.getString("sid"));
            details.setUniqueId(jsonObject.getString("ext"));
            list.add(details);
            return list;
        }catch (Exception e) {
            logger.error("txc buildSingleSmsSendResp error!result={}",result);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }

    public static List<SmsSendDetails> buildBatchSmsSendResp(String result)throws UmgException {
        Assert.notNull(result,"result not can blank");
        try{
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray array = jsonObject.getJSONArray("detail");

            List<SmsSendDetails> list = array.stream().map(o->{
                JSONObject object = (JSONObject) o;
                SmsSendDetails details = new SmsSendDetails();
                details.setMobile(object.getString("mobile"));
                details.setResId(object.getString("sid"));
                return details;
            }).collect(Collectors.toList());
            return list;
        }catch (Exception e) {
            logger.error("txc buildBatchSmsSendResp error!result={}",result);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }

    public static class Builder{

        private SmsConfig smsConfig;
        private Builder() {}

        public TxcSmsExecuter build(){
            Assert.notNull(smsConfig,"smsConfig can't empty");
            Assert.notNull(smsConfig.getTxcAppid(),"TxcAppid can't empty");
            Assert.notNull(smsConfig.getTxcAppkey(),"TxcAppKey can't empty");
            Assert.notNull(smsConfig.getLength(),"length can't empty");
            Assert.notNull(smsConfig.getReportNum(),"ReportNum can't empty");

            TxcSmsExecuter executer = new TxcSmsExecuter(smsConfig);
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
    }


}
