package org.sunso.parallel.demo.task;

import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.demo.parameter.DemoParallelResponse;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

public class DemoEmptyParallelTask implements IBaseParallelTask<DemoBizParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return DemoParallelTaskKey.DemoEmptyTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<DemoBizParameter> request) {
        int result = 1 / 0;
        return DemoParallelResponse.newBizSuccessResponse(getParallelTaskKey(),
                request.getRequest().getRequest() + ":empty");
    }
}
