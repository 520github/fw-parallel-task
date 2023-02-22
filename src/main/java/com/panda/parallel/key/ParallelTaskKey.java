package com.panda.parallel.key;

import com.panda.parallel.task.IBaseParallelTask;

public interface ParallelTaskKey {
    /**
     * 任务key
     * 
     * @return
     */
    public String getKey();

    /**
     * 任务key对应的任务
     * 
     * @return
     */
    IBaseParallelTask getIBaseParallelTask();
}
