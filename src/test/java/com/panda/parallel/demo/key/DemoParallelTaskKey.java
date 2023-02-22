package com.panda.parallel.demo.key;

import com.panda.parallel.demo.task.DemoBizFailParallelTask;
import com.panda.parallel.demo.task.DemoEmptyParallelTask;
import com.panda.parallel.demo.task.DemoSleepParallelTask;
import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.task.IBaseParallelTask;

public enum DemoParallelTaskKey implements ParallelTaskKey {
    DemoEmptyTask("demoEmptyTask", new DemoEmptyParallelTask()),
    DemoSleepTask("demoSleepTask", new DemoSleepParallelTask()),
    DemoBizFailTask("demoBizFailTask", new DemoBizFailParallelTask()),;

    private String key;
    private IBaseParallelTask iBaseParallelTask;

    DemoParallelTaskKey(String key) {
        this.key = key;
    }

    DemoParallelTaskKey(String key, IBaseParallelTask iBaseParallelTask) {
        this.iBaseParallelTask = iBaseParallelTask;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public IBaseParallelTask getIBaseParallelTask() {
        return iBaseParallelTask;
    }
}
