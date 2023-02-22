package com.panda.parallel.demo.task;

import com.panda.parallel.demo.key.DemoParallelTaskKey;
import com.panda.parallel.demo.parameter.DemoBizParameter;
import com.panda.parallel.demo.parameter.DemoParallelResponse;
import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;
import com.panda.parallel.task.IBaseParallelTask;
import org.springframework.stereotype.Component;

@Component
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
