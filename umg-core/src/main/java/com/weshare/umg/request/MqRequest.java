package com.weshare.umg.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weshare.umg.util.MqOperation;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/17 09:46
 * @Description:
 */
public class MqRequest {

    private MqOperation operation;
    private JSONObject object;
    private JSONArray array;

    public MqOperation getOperation() {
        return operation;
    }

    public void setOperation(MqOperation operation) {
        this.operation = operation;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }
}
