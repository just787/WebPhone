package com.wdl.web.frame;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * 输出信息到客户端工具类
 */
public class ResponseUtil {
    public static void response(String jsonBody, HttpServletRequest request, HttpServletResponse response) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
            OutputStream outputStream = byteArrayOutputStream;
            int origSize = jsonBody.getBytes().length;
            outputStream.write(jsonBody.getBytes("UTF-8"));
            outputStream.close();
            response.setHeader("Content-Length", String.valueOf(byteArrayOutputStream.size()));
            response.setHeader("Or-Content-Length", String.valueOf(origSize));

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");
            byteArrayOutputStream.writeTo(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("输出数据都客户端失败");
        }
    }

    public static void response(int code, HttpServletResponse response) {
        Responsex responsex = new Responsex();
        responsex.getResult(code);
        response(JSON.toJSONString(responsex), null, response);
    }
}
