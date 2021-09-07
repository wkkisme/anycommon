package com.anycommon.poi.annotation;

import java.lang.annotation.*;

/**
 * 截取
 * @author mac
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Split {

    String start();

    String end();

}
