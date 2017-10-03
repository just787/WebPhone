package com.wdl.web.frame;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器响应信息
 */
public class ServerResult {
    private Integer code;
    private String msg;
    private static Map<Integer, String> resultMap = new HashMap<Integer, String>();

    static {
        resultMap.put(0, "请求成功");
        resultMap.put(1, "请求方法不存在");
        resultMap.put(-1, "服务器内部错误");
    }

    public void setCode(Integer code) {
        this.code = code;
        this.msg = resultMap.get(code);
    }

    public String getMsg(int code) {
        return resultMap.get(code);
    }

    public ServerResult(int code) {
        this.code = code;
        if (resultMap.get(code) == null) {
            this.msg = resultMap.get(-1);
        } else {
            this.msg = resultMap.get(code);
        }
    }

}
