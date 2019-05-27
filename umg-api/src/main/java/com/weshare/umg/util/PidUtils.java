package com.weshare.umg.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @Author: finleyli
 * @Date: Created in 2019/3/19
 * @Describe:
 **/
public class PidUtils {
    private static Integer pid;

    /**
     * 获取当前进程号
     * @return
     */
    public static int getPid() {
        if (pid == null) {
            synchronized (PidUtils.class) {
                if (pid == null) {
                    pid = doGetPid();
                }
            }
        }
        return pid;
    }

    public static int doGetPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        try {
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            return -1;
        }
    }
}
