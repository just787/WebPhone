package com.wdl.web.service;

import com.wdl.web.frame.Responsex;

/**
 * 对应TestService接口都返回对象
 */
public class TestServiceResponsex extends Responsex {
    private String uId;
    private String name;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
