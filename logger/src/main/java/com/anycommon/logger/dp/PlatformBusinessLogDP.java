package com.anycommon.logger.dp;

import com.anycommon.poi.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@ApiModel(value="com.anycommon.logger.domain.HeroLandBusinessLog")
public class PlatformBusinessLogDP extends BaseDO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="id主键")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(value="appId")
    private String appId;

    /**
     * 
     */
    @ApiModelProperty(value="appName")
    private String appName;

    /**
     * 
     */
    @ApiModelProperty(value="orgCode")
    private String orgCode;

    /**
     * 入参
     */
    @ApiModelProperty(value="preParams入参")
    private String preParams;

    /**
     * 操作方法名称
     */
    @ApiModelProperty(value="operatorMethodName操作方法名称")
    private String operatorMethodName;

    public PlatformBusinessLogDP addCheck(){

        if (StringUtils.isAnyBlank(operatorMethodName,super.getCreator(),orgCode,appName,appId)){
            ResponseBodyWrapper.failParamException();
        }
        this.beforeInsert();
        return this;
    }
    /**
     * platform_business_log
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return app_id 
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 
     * @param appId 
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 
     * @return app_name 
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 
     * @param appName 
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * 
     * @return org_code 
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 
     * @param orgCode 
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 入参
     * @return pre_params 入参
     */
    public String getPreParams() {
        return preParams;
    }

    /**
     * 入参
     * @param preParams 入参
     */
    public void setPreParams(String preParams) {
        this.preParams = preParams == null ? null : preParams.trim();
    }

    /**
     * 操作方法名称
     * @return operator_method_name 操作方法名称
     */
    public String getOperatorMethodName() {
        return operatorMethodName;
    }

    /**
     * 操作方法名称
     * @param operatorMethodName 操作方法名称
     */
    public void setOperatorMethodName(String operatorMethodName) {
        this.operatorMethodName = operatorMethodName == null ? null : operatorMethodName.trim();
    }
}