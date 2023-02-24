package org.sunso.parallel;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.List;

/**
 * 并发任务执行接口类
 */
public interface ParallelTaskExecuteServiceI {

    /**
     * 并发执行指定任务列表并返回结果
     * 
     * @param request
     * @param parallelTaskKeys
     * @return
     */
    List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request, ParallelTaskKey... parallelTaskKeys);

    /**
     * 并发执行指定任务列表并返回结果
     * 
     * @param request
     * @param parallelTaskKeyList
     * @return
     */
    List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request,
            List<ParallelTaskKey> parallelTaskKeyList);
}
