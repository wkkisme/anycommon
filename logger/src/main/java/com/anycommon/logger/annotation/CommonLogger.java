package com.anycommon.logger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解，放到方法上，将会记录所有的入参
 * @datetime 2020-07-23
 * @author wangkai
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonLogger {

    String name();

}
