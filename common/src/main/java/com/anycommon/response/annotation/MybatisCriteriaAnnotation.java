package com.anycommon.response.annotation;


import com.anycommon.response.utils.MybatisCriteriaMethodEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bqhealth-cloud
 *  qo 上面的方法的注解,
 * @author wangkai
 * @date 2020/3/3
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MybatisCriteriaAnnotation {

    /**
     * 注解拼接方法 默认是and equal
     * @return
     */
    MybatisCriteriaMethodEnum method() default MybatisCriteriaMethodEnum.AND_EQUAL_TO;

    /**
     * 是否可为空
     * @return
     */
    boolean isCanBeNull() default true;
}
