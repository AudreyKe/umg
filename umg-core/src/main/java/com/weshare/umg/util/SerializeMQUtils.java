package com.weshare.umg.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weshare.json.JsonUtils;
import com.weshare.umg.request.MqRequest;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/17 09:43
 * @Description:
 */
public class SerializeMQUtils {

    public static  String smsDetailsSerialize(MqOperation operation, JSONArray array, JSONObject object){
        MqRequest mqRequest = new MqRequest();
        mqRequest.setOperation(operation);
        mqRequest.setArray(array);
        mqRequest.setObject(object);
        return JsonUtils.convertObjectToJSON(mqRequest);
    }

    public static MqRequest smsDetailsDeserialize(String body){
        MqRequest mqRequest = JsonUtils.toBean(body,MqRequest.class);
        return mqRequest;
    }
}
