package com.wdl.web.service;

import com.wdl.web.frame.RequestMapping;
import com.wdl.web.frame.Responsex;
import com.wdl.web.frame.ServerResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试接口
 */
public class TestService {

    private Responsex test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=======TestService test=======");

        TestServiceResponsex responsex = new TestServiceResponsex();
        responsex.setuId("t");
        responsex.setName("Tony");
        responsex.setResult(new ServerResult(0));

        return responsex;
    }

    @RequestMapping(value = "/test1")
    private Responsex test1(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=======TestService test1=======");

        TestServiceResponsex responsex = new TestServiceResponsex();
        responsex.setuId("a");
        responsex.setName("admin");
        responsex.setResult(new ServerResult(0));

        return responsex;
    }
}
