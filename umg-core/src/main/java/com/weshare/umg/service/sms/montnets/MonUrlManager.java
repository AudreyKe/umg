package com.weshare.umg.service.sms.montnets;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/8 10:58
 * @Description:
 */
public class MonUrlManager {

    private RedisTemplate<String, String> redisTemplate;
    private static final  String MON_PATH_KEY = "MON_PATH_KEY";

    public MonUrlManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取可用的地址信息
     * @return null：未获取到可用的地址信息;不为空:获取到可用的地址信息
     */
    public String getAvailableAddress() {
        String path = redisTemplate.opsForValue().get(MON_PATH_KEY);
        return path;
    }

    /**
     * 设置可用的地址信息
     * @return null：未获取到可用的地址信息;不为空:获取到可用的地址信息
     */
    public void setAvailableAddress(String url) {
        redisTemplate.opsForValue().set(MON_PATH_KEY,url);
    }



    public MonUrlManager setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }
}
