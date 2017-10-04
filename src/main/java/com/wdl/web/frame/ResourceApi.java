package com.wdl.web.frame;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义ServletApi
 */
public class ResourceApi extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Initor.initor.init();
    }

   /* @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);

        String[] paths = request.getRequestURL().toString().split("/");
        // 根据访问路径获取对应的action
        String action = paths[paths.length - 1];

        try {
            HandlerElement handlerElement = Initor.initor.getHandlerElement(action);
            if (handlerElement != null) {
                Responsex responsex = (Responsex) handlerElement.handle(request, response);
                ResponseUtil.response(new Gson().toJson(responsex), request, response);
            }
        } catch (ServerException s) {
            ResponseUtil.response(s.getCode(), response);
        } catch (Exception e) {
            ResponseUtil.response(-1, response);
        }
    }*/

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String[] paths = request.getRequestURL().toString().split("/");
        // 根据访问路径获取对应的action
        String action = paths[paths.length - 1];

        try {
            HandlerElement handlerElement = Initor.initor.getHandlerElement(action);
            if (handlerElement != null) {
                Responsex responsex = (Responsex) handlerElement.handle(request, response);
                ResponseUtil.response(JSON.toJSONString(responsex), request, response);
            }
        } catch (ServerException s) {
            ResponseUtil.response(s.getCode(), response);
        } catch (Exception e) {
            ResponseUtil.response(-1, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}
