package com.panda.parallel.task;

import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;

public interface IBaseParallelTask<B> {
    /**
     * 任务key
     * 
     * @return
     */
    ParallelTaskKey getParallelTaskKey();

    /**
     * 任务对应的执行入口
     * 
     * @param request
     * @return
     * @throws Exception
     */
    BaseParallelResponse taskEntry(BaseParallelRequest<B> request) throws Exception;

}
