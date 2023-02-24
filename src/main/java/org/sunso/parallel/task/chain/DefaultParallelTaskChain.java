package org.sunso.parallel.task.chain;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.task.IBaseParallelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过任务key枚举获取对应任务列表
 */
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
