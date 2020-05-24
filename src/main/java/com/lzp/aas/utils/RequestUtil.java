package com.lzp.aas.utils;

import com.lzp.aas.config.Constants;
import com.lzp.aas.exception.AppError;
import com.lzp.aas.model.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;

public class RequestUtil {

    private static void setProp(String key, Object value) {
        if (RequestContextHolder.getRequestAttributes() != null) {
            RequestContextHolder.currentRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
        }
    }

    private static Object getProp(String key) {
        Object value = null;
        try {
            value = RequestContextHolder.currentRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            //nothing to do
        }
        return value;
    }

    public static String getToken() {
        return (String) getProp(Constants.JWT_KEY);
    }

    public static void setToken(String token) {
        setProp(Constants.JWT_KEY, token);
    }

    public static User getUser() {
        return (User) getProp(Constants.USER);
    }

    public static void setUser(User user) {
        setProp(Constants.USER, user);
    }

    public static AppError getError() {
        return (AppError) getProp("code");
    }

    public static void setError(AppError e) {
        setProp("code", e);
    }

}
