package org.sunso.parallel.demo.parameter;

import org.sunso.parallel.parameter.BaseParallelRequest;

public class DemoParallelRequest extends BaseParallelRequest<DemoBizParameter> {
    public DemoParallelRequest(DemoBizParameter request) {
        super(request);
    }
}
