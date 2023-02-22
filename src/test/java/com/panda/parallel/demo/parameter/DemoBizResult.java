package com.panda.parallel.demo.parameter;

/**
 * 任务对应的业务结果
 */
public class DemoBizResult {
    private String name;
    private String title;

    public String getName() {
        return name;
    }

    public DemoBizResult setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DemoBizResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public static DemoBizResult newInstance() {
        DemoBizResult instance = new DemoBizResult();
        return instance;
    }
}
