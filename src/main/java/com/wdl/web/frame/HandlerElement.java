package com.wdl.web.frame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 处理对象和方法模版
 */
public class HandlerElement {
    private Object object;
    private Method method;

    public Object handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return method.invoke(object, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用方法失败!");
        }
        return null;
    }

    public HandlerElement(Object object, Method method) {
        this.object = object;
        this.method = method;
    }
}
