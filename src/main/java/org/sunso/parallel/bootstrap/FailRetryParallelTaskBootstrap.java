package org.sunso.parallel.bootstrap;

import org.sunso.parallel.ParallelTaskExecuteServiceFactory;
import org.sunso.parallel.ParallelTaskExecuteServiceI;
import org.sunso.parallel.parameter.FailRetryParallelRequest;

/**
 * 处理失败可重试并发任务的引导类
 */
public class FailRetryParallelTaskBootstrap extends SimpleParallelTaskBootstrap {

    protected FailRetryParallelTaskBootstrap() {
        request = FailRetryParallelRequest.create();
    }

    public static FailRetryParallelTaskBootstrap create() {
        return new FailRetryParallelTaskBootstrap();
    }

    /**
     * 设置处理失败情况的重试次数
     * 
     * @param failRetryNum
     * @return
     */
    public FailRetryParallelTaskBootstrap setFailRetryNum(int failRetryNum) {
        request.convertToFailRetryParallelRequest().setFailRetryNum(failRetryNum);
        return this;
    }

    protected ParallelTaskExecuteServiceI getParallelTaskExecuteServiceI() {
        return ParallelTaskExecuteServiceFactory.getFailRetryParallelTaskExecuteServiceInstance();
    }
}
