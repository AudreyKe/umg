package com.weshare.umg.util;

import com.weshare.wsmq.api.destination.Destination;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 14:56
 * @Description:
 */
public class DestinationUtils {

    public static Destination buildDestination(String dest){
        String[] dests = dest.split("-");
        if(dests.length < 4){
            throw new RuntimeException("dest error!dest="+dest);
        }

        Destination destination = new Destination();
        destination.setOrgCode(dests[0]).setServiceType(dests[1]).setServiceId(dests[2]).setIsSync(dests[3]);
        return destination;
    }

}
