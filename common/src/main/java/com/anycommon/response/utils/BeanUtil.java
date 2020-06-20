package com.anycommon.response.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.BaseDO;
import com.anycommon.response.common.ResponseBody;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangkai
 */
public class BeanUtil {

    /**
     * 插入时bean 之间拷贝  ? ---> DO
     *
     * @param source
     * @param target
     * @param <T>
     * @return 返回target
     * @throws Exception
     */
    public static <T extends BaseDO> T insertConversion(Object source, T target) throws Exception {
        conversion(source, target);
        target.beforeInsert();
        return target;
    }

    /**
     * 更新时bean 之间拷贝 ? ---> DO
     *
     * @param source
     * @param target
     * @param <T>
     * @return 返回target 对象
     * @throws Exception
     */
    public static <T extends BaseDO> T updateConversion(Object source, T target) throws Exception {
        conversion(source, target);
        target.beforeUpdate();
        return target;
    }

    /**
     * bean  vo --> DO 之间拷贝
     *
     * @param source
     * @param target
     * @param <T>
     * @return 返回target
     * @throws Exception
     */
    public static <T extends BaseDO> T conversion(Object source, T target) throws Exception {
        if (source == null) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * bean  vo --> DO 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target
     * @throws Exception
     */
    public static void copyProperties(Object source, Object target)  {

        BeanUtils.copyProperties(source, target);
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T, R> List<T> queryListConversion(List<R> source, Class<T> target) throws Exception {
        List<T> result = new ArrayList<>();
        for (Object s : source) {
            T t = target.newInstance();
            BeanUtils.copyProperties(s, t);
            result.add(t);
        }
        return result;
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝 包装成ResponseBody
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T, R> ResponseBody<List<T>> queryWrapperListConversion(ResponseBody<List<R>> source, Class<T> target) throws Exception {
        List<T> resultList = new ArrayList<>();
        List<R> data = source.getData();
        if (!CollectionUtil.isEmpty(data)) {
            for (Object s : data) {
                T t = target.newInstance();
                BeanUtils.copyProperties(s, t);
                resultList.add(t);
            }
        }
        ResponseBody<List<T>> result = new ResponseBody<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(resultList);
        return result;
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝 包装成ResponseBody
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T> ResponseBody<List<T>> queryWrapperListMapConversion(ResponseBody<List<Map>> source, Class<T> target) throws Exception {
        List<T> resultList = new ArrayList<>();
        List<Map> data = source.getData();
        if (!CollectionUtil.isEmpty(data)) {
            for (Object s : data) {
                T t = JSON.parseObject(JSON.toJSONString(s), target);
                resultList.add(t);
            }
        }
        ResponseBody<List<T>> result = new ResponseBody<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(resultList);
        return result;
    }

    /**
     * DO --> vo bean 之间拷贝 包装成ResponseBody
     *
     * @param source
     * @param target
     * @return 返回target
     * @throws Exception
     */
    public static <T, R> ResponseBody<T> queryWrapperConversion(ResponseBody<R> source, T target) throws Exception {

        BeanUtil.copyProperties(source.getData(), target);
        ResponseBody<T> result = new ResponseBody<>();
        result.setSuccess(source.isSuccess());
        result.setPage(source.getPage());
        result.setErrMsg(source.getErrMsg());
        result.setErrCode(source.getErrCode());
        result.setData(target);
        return result;
    }

    /**
     * 查询时list DO --> vo bean 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target list
     * @throws Exception
     */
    public static <T extends BaseDO, R> List<T> mappingListConversion(List<R> source, Class<T> target) throws Exception {
        List<T> result = new ArrayList<>();
        for (Object s : source) {
            T t = target.newInstance();
            BeanUtils.copyProperties(s, t);
            t.beforeUpdate();
            result.add(t);
        }
        return result;
    }

    /**
     * 查询时list do --> vo bean 之间拷贝
     *
     * @param source
     * @param target
     * @return 返回target
     * @throws Exception
     */
    public static <T> T queryConversion(Object source, T target) throws Exception {

        if (source == null) {
            return null;
        }

        BeanUtils.copyProperties(source, target);
        return target;
    }

}
