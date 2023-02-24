package org.sunso.parallel.demo.task;

import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.demo.parameter.DemoBizResult;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;
import org.springframework.stereotype.Component;

@Component
public class DemoBizFailParallelTask implements IBaseParallelTask<DemoBizParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return DemoParallelTaskKey.DemoBizFailTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<DemoBizParameter> request) throws Exception {
        DemoBizResult bizResult = DemoBizResult.newInstance().setName(request.getRequest().getRequest())
                .setTitle("gold");
        return BaseParallelResponse.newBizFailResponse(getParallelTaskKey(), bizResult);
    }
}
