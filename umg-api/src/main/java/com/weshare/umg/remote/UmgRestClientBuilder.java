package com.weshare.umg.remote;

import com.weshare.umg.system.ClientType;
import com.weshare.umg.util.ValidationUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/17 15:31
 * @Description:
 */
public class UmgRestClientBuilder {

    public static UmgRestClientBuilder create() {
        return new UmgRestClientBuilder();
    }

    /**
     * umg Client
     * @return
     */
    public UmgRestClient build() {
        ValidationUtils.validateUmConfig(umgConfig);
        Integer clientType = umgConfig.getClientType();
        ClientType type = ClientType.getByValue(clientType);
        switch (type){
            case HTTP:
                if(restTemplate == null){
                    restTemplate = new RestTemplate();
                }
                return new UmgRestClient(this);
            default:
                 throw new IllegalArgumentException("type Cannot use");

        }
    }

    private RestTemplate restTemplate;
    private UmgConfig umgConfig;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public UmgRestClientBuilder setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public UmgConfig getUmgConfig() {
        return umgConfig;
    }

    public UmgRestClientBuilder setUmgConfig(UmgConfig umgConfig) {
        this.umgConfig = umgConfig;
        return this;
    }


}
