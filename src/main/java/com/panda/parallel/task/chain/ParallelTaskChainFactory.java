package com.panda.parallel.task.chain;

public class ParallelTaskChainFactory {

    public static IBaseParallelTaskChain getDefaultParallelTaskChain() {
        return new DefaultParallelTaskChain();
    }
}
