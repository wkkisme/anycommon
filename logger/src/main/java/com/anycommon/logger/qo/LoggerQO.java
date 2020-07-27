package com.anycommon.logger.qo;

import com.anycommon.poi.common.BaseQO;

import java.io.Serializable;

public class LoggerQO  extends BaseQO implements Serializable {
    /**
     * appId 必填 可由后台自动获取或者自己传
     */
    private String appId;

    /**
     * orgCode 必填 可由后台自动获取或者自己传
     */
    private String orgCode;

    /**
     * appName 必填 可由后台自动获取或者自己传
     */
    private String appName;



    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
