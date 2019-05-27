package com.weshare.umg.response.base;


/**
 *  响应消息报文工具类
 *  @author xiaomudong
 */
public class ResultUtil {
    /**
     * 返回成功
     * @return
     */
    public static Result success(){
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMsg("操作成功");
        return result;
    }


    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    /**
     * 自定义信息
     * @param code
     * @param msg
     * @return
     */
    public static Result define(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
