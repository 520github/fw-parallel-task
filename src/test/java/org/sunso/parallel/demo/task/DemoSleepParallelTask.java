package org.sunso.parallel.demo.task;

import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.demo.parameter.DemoBizResult;
import org.sunso.parallel.demo.parameter.DemoParallelResponse;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

public class DemoSleepParallelTask implements IBaseParallelTask<DemoBizParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return DemoParallelTaskKey.DemoSleepTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<DemoBizParameter> request) throws Exception {
        Thread.sleep(15000);
        DemoBizResult bizResult = DemoBizResult.newInstance().setName(request.getRequest().getRequest())
                .setTitle("gold");
        return DemoParallelResponse.newResponse(getParallelTaskKey(), bizResult);
    }
}
