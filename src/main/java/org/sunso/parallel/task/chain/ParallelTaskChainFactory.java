package org.sunso.parallel.task.chain;

import org.sunso.parallel.helper.ApplicationContextHelper;
import org.sunso.parallel.parameter.BaseParallelRequest;

public class ParallelTaskChainFactory {
    public static IBaseParallelTaskChain getDefaultParallelTaskChain() {
        return new DefaultParallelTaskChain();
    }

    public static IBaseParallelTaskChain getAutoParallelTaskChain(BaseParallelRequest request) {
        if (request.isSpringTaskBean()) {
            return ApplicationContextHelper.getBean("applicationContextParallelTaskChain",
                    IBaseParallelTaskChain.class);
        }
        return getDefaultParallelTaskChain();
    }
}
