package com.anycommon.response.utils;

import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.page.Pagination;

import java.io.Serializable;

/**
 * 封装简单成功或失败 返回体
 */
public class ResponseBodyWrapper implements Serializable {


    public static <T> ResponseBody<T> success() {

        return success(ErrMsgEnum.SUCCESS);
    }

    public static <T> ResponseBody<T> success(ErrMsgEnum msg) {
        ResponseBody<T> result = new ResponseBody<>();
        result.setErrCode( msg.getErrorCode());
        result.setErrMsg(msg.getErrorMessage());
        return result;
    }


    public static <T> ResponseBody<T> fail(ErrMsgEnum msg) {
        ResponseBody<T> result = new ResponseBody<>();
        result.setErrCode( msg.getErrorCode());
        result.setErrMsg(msg.getErrorMessage());
        result.setSuccess(false);
        return result;
    }

    public static <T> ResponseBody<T> failSystemError() {

        return fail(ErrMsgEnum.ERROR_SYSTEM);
    }

    public static <T> ResponseBody<T> failParamError() {

        return fail(ErrMsgEnum.ERROR_PARAME);
    }

    public static <T> ResponseBody<T> successWrapper(T t,Long total, Integer pageSize,Integer pageIndex) {

        ResponseBody<T> tResponseBody = new ResponseBody<>();
        tResponseBody.setData(t);
        tResponseBody.setPage(new Pagination(pageIndex,pageSize,total));
        return tResponseBody;
    }
}
