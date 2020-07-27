package com.anycommon.logger.domain;

import com.anycommon.poi.common.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value="com.anycommon.logger.domain.PlatformBusinessLog")
public class PlatformBusinessLog extends BaseDO implements Serializable {
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
     * 
     */
    @ApiModelProperty(value="gmtCreate")
    private Date gmtCreate;

    /**
     * 
     */
    @ApiModelProperty(value="creator")
    private String creator;

    /**
     * 
     */
    @ApiModelProperty(value="gmtModified")
    private Date gmtModified;

    /**
     * 
     */
    @ApiModelProperty(value="modifier")
    private String modifier;

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
     * 
     * @return gmt_create 
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 
     * @param gmtCreate 
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 
     * @return creator 
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator 
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 
     * @return gmt_modified 
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 
     * @param gmtModified 
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 
     * @return modifier 
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 
     * @param modifier 
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
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