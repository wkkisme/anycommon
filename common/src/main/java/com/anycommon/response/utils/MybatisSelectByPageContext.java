package com.anycommon.response.utils;


import com.anycommon.response.common.BaseDO;
import com.anycommon.response.common.BaseQO;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.page.Pagination;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * anycommon-parent
 *
 * @author wangkai
 * @date 2020/6/8
 */

public class MybatisSelectByPageContext {

    public static <T, Mapper, Example> ResponseBody<List<T>> selectListPage(Mapper mapper,Class<Mapper> mapperClass, Class<Example> example, BaseQO qo) throws Throwable {
        // 原本的mapper 为动态代理后的对象 ，需要从Proxy中获取真实mapperProxy对象
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(mapper);
        MapperProxy<Mapper> mapperProxy = (MapperProxy<Mapper>) invocationHandler;
        Class<? extends MapperProxy> mapperProxyClass = mapperProxy.getClass();

        // 获取sqlsession 和 真实interface也就是真实mapper
        Field sqlSessionFiled = mapperProxyClass.getDeclaredField("sqlSession");
        Field mapperInterface = mapperProxyClass.getDeclaredField("mapperInterface");
        mapperInterface.setAccessible(true);
        sqlSessionFiled.setAccessible(true);
        SqlSession session = (SqlSession) sqlSessionFiled.get(mapperProxy);


        Object o = example.newInstance();
        Object examples = MybatisCriteriaConditionUtil.createExample(o, qo);

        //从session中获取到真实的mapper对象
        Object realMapperValue = mapperInterface.get(mapperProxy);
        Object realMapperObject = session.getMapper(mapperClass);
        // 获取
        Method countByExample = mapperClass.getMethod("countByExample",example);
        Object[] objects = new Object[1];
        objects[0]= qo;

        Object invoke1 = mapperProxy.invoke(realMapperObject, countByExample, objects);
        Method selectByExample = mapperClass.getMethod("selectByExample",example);
        Object invoke = selectByExample.invoke(mapper, examples);
        ResponseBody<List<T>> result = new ResponseBody<>();
        result.setData((List<T>) invoke);

        result.setPage(new Pagination(qo.getPageIndex(), qo.getPageSize(), (Long) countByExample.invoke(mapper, examples)));

        return result;
    }


    public static <T, Mapper> ResponseBody<T> selectOne(Mapper mapper, Object key) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(mapper);
        MapperProxy mapperProxy = (MapperProxy) invocationHandler;
        Field sqlSessionFiled = mapperProxy.getClass().getDeclaredField("sqlSession");
        sqlSessionFiled.setAccessible(true);
        SqlSession session = (SqlSession) sqlSessionFiled.get(mapperProxy);

        Object realMapper = session.getMapper(mapper.getClass());

        Method selectByExample = realMapper.getClass().getMethod("selectByPrimaryKey");

        Object invoke = selectByExample.invoke(mapper, key);

        ResponseBody<T> result = new ResponseBody<>();
        result.setData((T) invoke);
        return result;
    }


    public static <Mapper> ResponseBody<Boolean> insert(Mapper mapper, BaseDO baseDO) throws Exception {

        InvocationHandler invocationHandler = Proxy.getInvocationHandler(mapper);
        MapperProxy mapperProxy = (MapperProxy) invocationHandler;
        Field sqlSessionFiled = mapperProxy.getClass().getDeclaredField("sqlSession");
        sqlSessionFiled.setAccessible(true);
        SqlSession session = (SqlSession) sqlSessionFiled.get(mapperProxy);

        Object realMapper = session.getMapper(mapper.getClass());

        Method selectByExample = realMapper.getClass().getMethod("insert");
        baseDO.beforeInsert();
        selectByExample.invoke(mapper, baseDO);

        ResponseBody<Boolean> result = new ResponseBody<>();
        result.setData(true);
        return result;
    }


    public static < Mapper> ResponseBody<Boolean> update(Mapper mapper, BaseDO baseDO) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(mapper);
        MapperProxy mapperProxy = (MapperProxy) invocationHandler;
        Field sqlSessionFiled = mapperProxy.getClass().getDeclaredField("sqlSession");
        sqlSessionFiled.setAccessible(true);
        SqlSession session = (SqlSession) sqlSessionFiled.get(mapperProxy);

        Object realMapper = session.getMapper(mapper.getClass());
        Method selectByExample = realMapper.getClass().getMethod("updateByExample");
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
