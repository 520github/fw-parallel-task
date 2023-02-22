package com.panda.parallel.task;

import com.panda.parallel.exception.ParallelTaskException;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;

import java.util.concurrent.Callable;

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
