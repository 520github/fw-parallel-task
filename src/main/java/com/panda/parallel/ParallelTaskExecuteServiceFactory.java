package com.panda.parallel;

public class ParallelTaskExecuteServiceFactory {

    public static ParallelTaskExecuteServiceI getSimpleParallelTaskExecuteServiceInstance() {
        return SimpleParallelTaskExecuteServiceHolder.INSTANCE;
    }

    public static ParallelTaskExecuteServiceI getFailRetryParallelTaskExecuteServiceInstance() {
        return FailRetryParallelTaskExecuteServiceHolder.INSTANCE;
    }

    private static class SimpleParallelTaskExecuteServiceHolder {
        private static final SimpleParallelTaskExecuteService INSTANCE = new SimpleParallelTaskExecuteService();
    }

    private static class FailRetryParallelTaskExecuteServiceHolder {
        private static final FailRetryParallelTaskExecuteService INSTANCE = new FailRetryParallelTaskExecuteService();
    }
}
