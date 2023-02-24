package org.sunso.parallel.task;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;

/**
 * 并发任务接口
 * 
 * @param <B>
 */
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
