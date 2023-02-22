package com.panda.parallel.task.chain;

import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.task.IBaseParallelTask;

import java.util.List;

public interface IBaseParallelTaskChain {

    /**
     * 根据任务key列表获取对应的任务列表
     * 
     * @param keys
     * @return
     */
    List<IBaseParallelTask> getParallelTaskList(List<ParallelTaskKey> keys);
}
