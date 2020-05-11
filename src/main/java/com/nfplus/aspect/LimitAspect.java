package com.nfplus.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.nfplus.annotation.RequestLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/17 23:06
 */

@Component
@Scope
@Aspect
public class LimitAspect {

    //限制流量数量定义
    private static RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    /**
     * 切入点引入
     */
    @Pointcut("@annotation(com.nfplus.annotation.RequestLimit)")
    public  void requestAspect(){}


    @Around("requestAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();

        MethodSignature methodSignature = (MethodSignature) signature;

        Method targetMthod = methodSignature.getMethod();
        if (targetMthod.isAnnotationPresent(RequestLimit.class)){
            RequestLimit requestLimit = targetMthod.getAnnotation(RequestLimit.class);
            rateLimiter.setRate(requestLimit.perSecond());
            if (!rateLimiter.tryAcquire(requestLimit.timeOut(), requestLimit.timeOutUnit())){
                return "服务器繁忙，请稍后再试!";
            }
        }
        return  joinPoint.proceed();
    }

}
