package com.weshare.umg.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 18:51
 * @Description:
 */
public class UuIdUtils {
    public static final String createUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}