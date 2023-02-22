package com.panda.parallel.demo.parameter;

import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelResponse;

public class DemoParallelResponse extends BaseParallelResponse<DemoBizResult> {

    public static DemoParallelResponse newResponse(ParallelTaskKey parallelTaskKey, DemoBizResult bizResult) {
        DemoParallelResponse response = new DemoParallelResponse();
        response.setParallelTaskKey(parallelTaskKey);
        response.setData(bizResult);
        response.setBizSuccess(true);
        return response;
    }
}
