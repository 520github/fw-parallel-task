package org.sunso.parallel.demo.parameter;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelResponse;

public class DemoParallelResponse extends BaseParallelResponse<DemoBizResult> {

    public static DemoParallelResponse newResponse(ParallelTaskKey parallelTaskKey, DemoBizResult bizResult) {
        DemoParallelResponse response = new DemoParallelResponse();
        response.setParallelTaskKey(parallelTaskKey);
        response.setData(bizResult);
        response.setBizSuccess(true);
        return response;
    }
}
