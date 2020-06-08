package com.anycommon.response.utils;


import com.anycommon.response.common.BaseDO;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;

import java.lang.reflect.Method;
import java.util.List;

/**
 * anycommon-parent
 *
 * @author wangkai
 * @date 2020/6/8
 */

public class MybatisSelectByPageContext {

    public static <T, Mapper, Example> ResponseBody<List<T>> selectListPage(Mapper mapper, Class<Example> example, BaseQO qo) throws Exception {

        Method selectByExample = mapper.getClass().getMethod("selectByExample");
        Method countByExample = mapper.getClass().getMethod("countByExample");
        Object o = example.newInstance();
        Object examples = MybatisCriteriaConditionUtil.createExample(o, qo);

        Object invoke = selectByExample.invoke(mapper, examples);

        ResponseBody<List<T>> result = new ResponseBody<>();
        result.setData((List<T>) invoke);

        result.setPage(new Pagination(qo.getPageIndex(), qo.getPageSize(), (Long) countByExample.invoke(mapper, examples)));

        return result;
    }


    public static <T, Mapper> ResponseBody<T> selectOne(Mapper mapper, Object key) throws Exception {

        Method selectByExample = mapper.getClass().getMethod("selectByPrimaryKey");

        Object invoke = selectByExample.invoke(mapper, key);

        ResponseBody<T> result = new ResponseBody<>();
        result.setData((T) invoke);
        return result;
    }


    public static <Mapper> ResponseBody<Boolean> insert(Mapper mapper, BaseDO baseDO) throws Exception {

        Method selectByExample = mapper.getClass().getMethod("insert");
        baseDO.beforeInsert();
        selectByExample.invoke(mapper, baseDO);

        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(true);
        return result;
    }


    public static < Mapper> ResponseBody<Boolean> update(Mapper mapper, BaseDO baseDO) throws Exception {

        Method selectByExample = mapper.getClass().getMethod("updateByExample");
        baseDO.beforeUpdate();
        selectByExample.invoke(mapper, baseDO);

        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(true);
        return result;
    }



    public static < Mapper> ResponseBody<Boolean> delete(Mapper mapper, BaseDO baseDO) throws Exception {
        baseDO.setIsDeleted(true);
        return update(mapper,baseDO);
    }




}
