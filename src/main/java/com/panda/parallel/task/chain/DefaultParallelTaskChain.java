package com.panda.parallel.task.chain;

import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.task.IBaseParallelTask;

import java.util.ArrayList;
import java.util.List;

public class DefaultParallelTaskChain implements IBaseParallelTaskChain {

    @Override
    public List<IBaseParallelTask> getParallelTaskList(List<ParallelTaskKey> keys) {
        List<IBaseParallelTask> resultList = new ArrayList<>();
        keys.forEach(parallelTaskKey -> {
            if (parallelTaskKey != null && parallelTaskKey.getIBaseParallelTask() != null) {
                resultList.add(parallelTaskKey.getIBaseParallelTask());
            }
        });
        return resultList;
    }
}
