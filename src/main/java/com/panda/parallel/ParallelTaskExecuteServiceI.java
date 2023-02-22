package com.panda.parallel;

import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;

import java.util.List;

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
