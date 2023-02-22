package com.panda.parallel.demo.parameter;

/**
 * 任务对应的业务请求参数
 */
public class DemoBizParameter {
    private String request;

    public String getRequest() {
        return request;
    }

    public DemoBizParameter setRequest(String request) {
        this.request = request;
        return this;
    }

    public static DemoBizParameter newInstance() {
        return new DemoBizParameter();
    }
}
