package com.anycommon.oss.dto;

import java.io.Serializable;

/**
 * oss dto
 */
public class OssDTO implements Serializable {


    /**
     * 本次上传的文件的唯一key
     */
    private String key;

    /**
     * 如果不是私有化的 则会返回公网的一个地址
     */
    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OssDTO{" +
                "key='" + key + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
