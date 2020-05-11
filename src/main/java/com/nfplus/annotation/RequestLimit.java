package com.nfplus.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/17 23:03
 */

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {

    String value() default "";

    /**
     * 令牌数量，默认最大（不限流）
     * @return
     */
    double perSecond() default Double.MAX_VALUE;

    /**
     * 超时等待时间 默认0 则代表非阻塞
     * @return
     */
    int timeOut() default 0;

    /**
     * 单位时间
     * @return
     */
    TimeUnit timeOutUnit() default TimeUnit.MILLISECONDS;
}
