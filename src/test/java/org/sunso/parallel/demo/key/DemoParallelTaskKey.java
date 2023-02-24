package org.sunso.parallel.demo.key;

import org.sunso.parallel.demo.task.DemoBizFailParallelTask;
import org.sunso.parallel.demo.task.DemoEmptyParallelTask;
import org.sunso.parallel.demo.task.DemoSleepParallelTask;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.task.IBaseParallelTask;

public enum DemoParallelTaskKey implements ParallelTaskKey {
    DemoEmptyTask("demoEmptyTask", new DemoEmptyParallelTask()), DemoSleepTask("demoSleepTask",
            new DemoSleepParallelTask()), DemoBizFailTask("demoBizFailTask", new DemoBizFailParallelTask()),;

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
