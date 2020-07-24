package com.anycommon.logger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 日志用户需要配置
 */
@Configuration
public class LoggerConfig {


    @Value("${platform.logger.appId}")
    private String appId;

    @Value("${platform.logger.appName}")
    private String appName;

    @Value("${platform.logger.orgCode}")
    private String orgCode;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
