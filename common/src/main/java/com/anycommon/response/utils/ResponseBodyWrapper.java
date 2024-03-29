package com.anycommon.response.utils;

import com.anycommon.response.common.BaseQO;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.constant.ErrMsgEnum;
import com.anycommon.response.expception.AppSystemException;
import com.anycommon.response.page.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 封装简单成功或失败 返回体
 */
public class ResponseBodyWrapper implements Serializable {


    public static <T> ResponseBody<T> success() {

        return success(ErrMsgEnum.SUCCESS);
    }

    public static <T> ResponseBody<T> success(ErrMsgEnum msg) {
        ResponseBody<T> result = new ResponseBody<>();
        result.setErrCode(msg.getErrorCode());
        result.setErrMsg(msg.getErrorMessage());
        return result;
    }


    public static <T> ResponseBody<T> fail(ErrMsgEnum msg) {
        ResponseBody<T> result = new ResponseBody<>();
        result.setErrCode(msg.getErrorCode());
        result.setErrMsg(msg.getErrorMessage());
        result.setSuccess(false);
        return result;
    }


    public static <T> ResponseBody<T> fail(String msg, String code) {
        ResponseBody<T> result = new ResponseBody<>();
        result.setErrCode(code);
        result.setErrMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static AppSystemException failException(String msg) {

        throw new AppSystemException(msg);
    }

    public static void failParamException() {

        throw new AppSystemException(ErrMsgEnum.EMPTY_PARAME.getErrorMessage());
    }

    public static void failSysException() {

        throw new AppSystemException(ErrMsgEnum.ERROR_SYSTEM.getErrorMessage());
    }

    public static <T> ResponseBody<T> failSystemError() {

        return fail(ErrMsgEnum.ERROR_SYSTEM);
    }

    public static <T> ResponseBody<T> failParamError() {

        return fail(ErrMsgEnum.ERROR_PARAME);
    }


    public static <T, R> ResponseBody<List<R>> successListWrapper(List<T> t, Long total, BaseQO qo, Class<R> swap) {

        ResponseBody<List<R>> tResponseBody = new ResponseBody<>();
        try {
            tResponseBody.setData(BeanUtil.queryListConversion(t, swap));
        } catch (Exception e) {
            throw new AppSystemException(e);
        }
        if (!qo.getNeedPage()){
            tResponseBody.setPage(new Pagination(1, Math.toIntExact(total), total));
        }else {
            tResponseBody.setPage(new Pagination(qo.getPageIndex(), qo.getPageSize(), total));
        }
        return tResponseBody;
    }

    public static <T, R> ResponseBody<R> successWrapper(T t, Class<R> swap) {

        ResponseBody<R> tResponseBody = new ResponseBody<>();
        try {
            R r = swap.newInstance();
            tResponseBody.setData(BeanUtil.queryConversion(t, r));
        } catch (Exception e) {
            throw new AppSystemException(e);
        }
        return tResponseBody;
    }

    public static <T> ResponseBody<T> successWrapper(T t) {

        ResponseBody<T> tResponseBody = new ResponseBody<>();
        try {

            tResponseBody.setData(t);
        } catch (Exception e) {
            throw new AppSystemException(e);
        }
        return tResponseBody;
    }
}
