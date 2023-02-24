package org.sunso.parallel.task;

import org.sunso.parallel.exception.ParallelTaskException;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.concurrent.Callable;

/**
 * 对具体并发任务进行包装
 */
public class ParallelTaskCall implements Callable<BaseParallelResponse> {
    private IBaseParallelTask task;
    private BaseParallelRequest request;

    public ParallelTaskCall(BaseParallelRequest request, IBaseParallelTask task) {
        this.task = task;
        this.request = request;
    }

    @Override
    public BaseParallelResponse call() throws Exception {
        try {
            return task.taskEntry(request);
        } catch (Exception e) {
            return BaseParallelResponse.newExceptionResponse(task.getParallelTaskKey(),
                    ParallelTaskException.create(task.getParallelTaskKey(), e));
        }
    }
}
