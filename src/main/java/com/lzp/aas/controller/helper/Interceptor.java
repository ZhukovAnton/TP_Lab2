package com.lzp.aas.controller.helper;

import com.lzp.aas.config.Constants;
import com.lzp.aas.exception.AppException;
import com.lzp.aas.exception.HttpAppError;
import com.lzp.aas.model.Session;
import com.lzp.aas.repository.SessionRepository;
import com.lzp.aas.utils.RequestUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
public class Interceptor extends HandlerInterceptorAdapter {

    private final static String ALLOW_HEADERS = "origin, authorization, accept, content-type, x-requested-with, X-Cache-Type, X-Cache-Use, X-Cache-Time, pragma, cache-control, " + Constants.JWT_KEY;

    private final SessionRepository sessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getServletPath().equals("/error")) {
            return true;
        }

        response.addHeader("Access-Control-Expose-Headers", ALLOW_HEADERS);
        response.addHeader("Access-Control-Allow-Headers", ALLOW_HEADERS);
        response.addHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if (request.getMethod().equals("OPTIONS")) {
            return false;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ApiOperation annotation = handlerMethod.getMethodAnnotation(ApiOperation.class);
            if (annotation != null && annotation.authorizations().length > 0) {
                for (Authorization authorization : annotation.authorizations()) {
                    if (authorization.value().equals(Constants.JWT_AUTH)) {
                        String token = request.getHeader(Constants.JWT_KEY);
                        if (token == null || token.isEmpty() || !token.startsWith("Token token=")) {
                            throw new AppException(HttpAppError.UNAUTHORIZED);
                        }
                        token = token.replace("Token token=", "");
                        Optional<Session> session = sessionRepository.findByToken(token);
                        if (session.isEmpty()) {
                            throw new AppException(HttpAppError.UNAUTHORIZED);
                        }
                        RequestUtil.setToken(session.get().getToken());
                        RequestUtil.setUser(session.get().getUser());
                    }
                }
            }
        }
        return true;
    }

}
