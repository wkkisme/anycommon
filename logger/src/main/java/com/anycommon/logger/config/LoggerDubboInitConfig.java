package com.anycommon.logger.config;

import com.platform.sso.facade.PlatformSsoAclServiceFacade;
import com.platform.sso.facade.PlatformSsoServiceFacade;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerDubboInitConfig {

    @Reference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoServiceFacade platformSsoServiceFacade;

    @Reference(group = "platform-sso",version = "1.0.0",check = false)
    private PlatformSsoAclServiceFacade platformSsoAclServiceFacade;

    @Bean("platformSsoService")
    public PlatformSsoServiceFacade getPlatformSsoServiceFacade(){

        return platformSsoServiceFacade;
    }

    @Bean("platformSsoAclService")
    public PlatformSsoAclServiceFacade getPlatformSsoAclServiceFacade(){

        return platformSsoAclServiceFacade;
    }
}
