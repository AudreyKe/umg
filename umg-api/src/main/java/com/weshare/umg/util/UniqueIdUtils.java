package com.weshare.umg.util;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: finleyli
 * @Date: Created in 2019/3/19
 * @Describe: UniqueId 编码规则：
 * UniqueId长度为24位字符串，由两个16进制数字拼接
 * 00-08位为精确到秒的时间戳
 * 08-16位为生成ID的机器IP（32位长整型数字），
 * 16-20位为进程ID（以10000取模）
 * 20-24位为递增序号，增至32768时重置为0
 **/
public class UniqueIdUtils {

    private static final AtomicLong COUNT_OR = new AtomicLong();

    /**
     * 获取UniqueId
     * @return
     */
    public static String nextId() {
        return createUniqueId();
    }

    public static String createUniqueId() {
        //Time
        long current = System.currentTimeMillis() / 1000;
        //IP
        String ip = IpUtils.getLocalIp();
        Long ipNum = IpUtils.ipv4ToLong(ip);
        //PID
        Long pid = PidUtils.getPid() % 10000L;
        //Seq Num
        Long seqNum = COUNT_OR.incrementAndGet();

        if (COUNT_OR.get() >= 32768) {
            synchronized (UniqueIdUtils.class) {
                if (COUNT_OR.get() >= 32768) {
                    COUNT_OR.set(0L);
                }
            }
        }

        String prefix = Long.toHexString(current);
        Long surfixLong = (ipNum << 32) | (pid << 16) | (seqNum);
        String surfix = StringUtils.leftPad(Long.toHexString(surfixLong), 16, "0");
//
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(surfix);
        return stringBuilder.toString();
    }

    /**
     * 解析UniqueId
     * @param id
     * @return
     */
    public static UniqueIdEntity decodeId(String id) {
        UniqueIdEntity entity = new UniqueIdEntity();

        int size = id.length();

        String time = id.substring(0, size - 16);
        Long timestamp = Long.parseLong(time, 16) * 1000L;

        String ipStr = id.substring(size - 16, size - 8);
        String ip = IpUtils.longToIpv4(Long.parseLong(ipStr, 16));

        String pidStr = id.substring(size - 8, size - 4);
        Long pid = Long.parseLong(pidStr, 16);

        entity.setTimestamp(timestamp);
        entity.setIp(ip);
        entity.setPid(String.valueOf(pid));
        return entity;
    }
}
