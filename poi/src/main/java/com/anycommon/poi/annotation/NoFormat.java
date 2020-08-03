package com.anycommon.poi.annotation;

/**
 * 不保存样式
 * @author wangk
 * @date 2020-08-01 13:18
 */

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoFormat {
    String[] value() default {""};
}
