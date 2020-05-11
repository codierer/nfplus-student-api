package com.nfplus.util;

import com.sun.xml.internal.ws.client.ResponseContext;
import org.omg.CORBA.portable.ResponseHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zac
 * @description: Http请求上下文
 * @data 2020 2020/3/17 12:09
 */
public class HttpContextUtil {

    private HttpContextUtil(){}

    /**
     * 获取Http请求
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    public static HttpServletResponse getHttpServletResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

}
