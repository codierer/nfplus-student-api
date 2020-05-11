package com.nfplus.aspect;

import com.alibaba.fastjson.JSON;
import com.nfplus.annotation.MyLog;
import com.nfplus.bean.SysExLog;
import com.nfplus.bean.SysOpLog;
import com.nfplus.service.SysExLogService;
import com.nfplus.service.SysOpLogService;
import com.nfplus.util.HttpContextUtil;
import com.nfplus.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author zac
 * @description:切面类
 * @data 2020 2020/3/17 11:50
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysOpLogService sysOpLogService;

    @Autowired
    private SysExLogService sysExLogService;

    /**
     * 定义操作切入点
     */
    @Pointcut("@annotation(com.nfplus.annotation.MyLog)")
    public void sysOplogPoint(){}

    /**
     * 定义异常切入点
     */
    @Pointcut("execution(* com.nfplus.controller..*.*(..))")
    public void sysExlogPoint(){}




    @AfterReturning(value = "sysOplogPoint()",returning = "keys")
    public void saveSysOpLog(JoinPoint joinPoint,Object keys){
        //System.out.println("=============Point start==============");
        SysOpLog sysOpLog = new SysOpLog();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            //切入点方法
            Method method = signature.getMethod();
            MyLog myLog = method.getAnnotation(MyLog.class);
            //获取操作
            if (myLog != null) {
                String value = myLog.value();
                sysOpLog.setOperation(value);
            }
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            sysOpLog.setMethod(className + ": " + methodName);
            //获取参数
            Object args[] = joinPoint.getArgs();
            String params = JSON.toJSONString(args);
            sysOpLog.setRequParams(params);//请求参数
            sysOpLog.setRespParams(JSON.toJSONString(keys));
            //获取时间点
            sysOpLog.setCreateTime(new Date());
            //获取ip地址
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            String ip = IpUtils.getIpAddr(request);
            sysOpLog.setIpAddr(ip);
            String mac = IpUtils.getMACAddress();
            sysOpLog.setMacAddr(mac);
            String url = request.getRequestURL().toString();
            String uri = request.getRequestURI();
            sysOpLog.setUrl(url);
            HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
            //int status = response.getStatus();
            sysOpLog.setStatus(response.getStatus());
            //response.get
            //sysLog.
            sysOpLogService.insert(sysOpLog);
        }catch (Exception te){
            te.printStackTrace();
        }
    }


    @AfterThrowing(pointcut = "sysExlogPoint()", throwing = "e")
    public void saveSysExLog(JoinPoint joinPoint, Throwable e){
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        SysExLog sysExLog = new SysExLog();

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            sysExLog.setMethod(className + ": " + methodName);

            //获取参数
            Object args[] = joinPoint.getArgs();
            String params = JSON.toJSONString(args);
            sysExLog.setRequParams(params);//请求参数
            sysExLog.setName(e.getClass().getName());//异常名称
            sysExLog.setMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            sysExLog.setUrl(request.getRequestURI());
            sysExLog.setIpAddr(IpUtils.getIpAddr(request));
            sysExLog.setCreateTime(new Date());
            sysExLogService.insert(sysExLog);
        }catch (Exception te){
            te.printStackTrace();
        }

    }

    /**
     * 转换异常信息为字符串
     * @param exceptionName
     * @param exceptionMessage
     * @param elements
     * @return
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
             strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
}

}
