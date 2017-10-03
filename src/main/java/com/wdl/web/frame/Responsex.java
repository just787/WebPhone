package com.wdl.web.frame;

/**
 * 返回客户端信息对象的顶级类
 */
public class Responsex {
    private ServerResult result;

    public ServerResult getResult(int code) {
        if (result == null) {
            result = new ServerResult(code);
        }

        return result;
    }

    public void setResult(ServerResult result) {
        this.result = result;
    }
}
