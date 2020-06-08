package com.anycommon.response.common;

import java.util.Date;

/**
 * @author wangkai
 */
public class BaseDO {

    private Long id;

    private String creator;

    private Date gmtCreate;

    private Date gmtModified;

    private String modifier;

    private Boolean isDeleted;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void beforeUpdate(){
        this.isDeleted = false;
        this.gmtModified = new Date();
    }

    public void beforeUpdate(String operator){
        this.isDeleted = false;
        this.modifier = operator;
        this.gmtModified = new Date();
    }



    public void beforeInsert(){
        this.gmtModified = new Date();
        this.gmtCreate = new Date();
        this.isDeleted = false;
    }
    public void beforeInsert(String operator){
        this.gmtModified = new Date();
        this.gmtCreate = new Date();
        this.creator = operator;
        this.modifier = operator;
        this.isDeleted = false;
    }



    public void beforeDelete(){
        this.gmtModified = new Date();
        this.isDeleted = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "BaseDO{" +
                "id=" + id +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", modifier='" + modifier + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
