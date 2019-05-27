package com.weshare.umg.util;

import java.util.Date;


public class UniqueIdEntity {
    private String ip;
    private String pid;
    private Long timestamp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UniqueIdEntity{");
        sb.append("ip='").append(ip).append('\'');
        sb.append(", pid='").append(pid).append('\'');
        sb.append(", timestamp=").append(timestamp).append('\'');
        sb.append(", date=").append(new Date(timestamp));
        sb.append('}');
        return sb.toString();
    }
}
