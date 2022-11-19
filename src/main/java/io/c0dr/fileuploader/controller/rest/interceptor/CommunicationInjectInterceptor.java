package io.c0dr.fileuploader.controller.rest.interceptor;

import io.c0dr.fileuploader.config.RequestMetaDataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static io.c0dr.fileuploader.controller.rest.config.HeaderNames.CORRELATION_ID;
import static io.c0dr.fileuploader.controller.rest.config.HeaderNames.USER_NAME;

@Component
public class CommunicationInjectInterceptor implements HandlerInterceptor {

    public static final String NOT_DEF = "--N/A--";

    @Autowired
    RequestMetaDataBean user;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        user.setUserName(request.getHeader(USER_NAME) != null ? request.getHeader(USER_NAME) : NOT_DEF);
        user.setCorrelationId(request.getHeader(CORRELATION_ID) != null ? request.getHeader(CORRELATION_ID) : UUID.randomUUID().toString());

        response.setHeader(CORRELATION_ID, user.getCorrelationId());

        return true;
    }
}
