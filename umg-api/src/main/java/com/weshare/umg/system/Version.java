package com.weshare.umg.system;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/14
 * @Description:
 */
public enum Version {
    V1("v1.0");

    private String v;

    Version(String v) {
        this.v = v;
    }

    public String getV() {
        return v;
    }

    public static Version getVersion(String v) {
        Version[] values = Version.values();
        for (Version value : values) {
            if (value.v.equals(v)) {
                return value;
            }
        }
        return null;
    }
}
