package com.wdl.web.frame;

/**
 * 自定义异常
 */
public class ServerException extends RuntimeException {
    private int code;

    public ServerException(Throwable t) {
        super(t);
    }

    public ServerException(Exception e) {
        super(e);
    }

    public ServerException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
