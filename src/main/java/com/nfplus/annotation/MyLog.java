package com.nfplus.annotation;

import java.lang.annotation.*;

/**
 * @author zac
 * @description:定义@MyLog注解
 * @data 2020 2020/3/17 11:23
 */

@Target(ElementType.METHOD)//目标级别
@Retention(RetentionPolicy.RUNTIME)//执行阶段
@Documented
public @interface MyLog {

    String value() default "";
}
