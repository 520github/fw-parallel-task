package com.panda.parallel.demo.parameter;

import com.panda.parallel.parameter.BaseParallelRequest;

public class DemoParallelRequest extends BaseParallelRequest<DemoBizParameter> {
    public DemoParallelRequest(DemoBizParameter request) {
        super(request);
    }
}
