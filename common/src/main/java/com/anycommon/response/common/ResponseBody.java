package com.anycommon.response.common;

import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.page.Pagination;

import java.io.Serializable;

public class ResponseBody<T> implements Serializable {

    /**
     * 相应成功失败，默认成功
     */
    private boolean success = true;

    /**
     * 返回内容
     */
    private T data;

    /**
     * 错误代码
     */
    private String errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 分页
     */
    private Pagination page;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Pagination getPage() {
        return page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }
}
