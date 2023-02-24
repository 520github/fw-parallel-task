package org.sunso.parallel.key;

import org.sunso.parallel.task.IBaseParallelTask;

/**
 * 并发任务key接口
 */
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
