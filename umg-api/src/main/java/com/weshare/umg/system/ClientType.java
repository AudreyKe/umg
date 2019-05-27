package com.weshare.umg.system;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/4/18
 * @Description:
 */
public enum ClientType {
    HTTP(1),FEIGN(2),MQ(3);

    private int type;

    ClientType(int type) {
        this.type = type;
    }

    public static ClientType getByValue(Integer type) {
        ClientType[] clientTypes = ClientType.values();
        for (ClientType clientType : clientTypes) {
            if (clientType.type == type) {
                return clientType;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }
}
