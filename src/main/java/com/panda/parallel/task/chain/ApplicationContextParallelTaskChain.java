package com.panda.parallel.task.chain;

import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.task.IBaseParallelTask;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationContextParallelTaskChain implements IBaseParallelTaskChain, ApplicationContextAware {
    ApplicationContext applicationContext;

    @Override
    public List<IBaseParallelTask> getParallelTaskList(List<ParallelTaskKey> keys) {
        return getParallelTaskListInner(keys);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    private List<IBaseParallelTask> getParallelTaskListInner(List<ParallelTaskKey> keys) {
        Map<String, IBaseParallelTask> taskMap = applicationContext.getBeansOfType(IBaseParallelTask.class);
        taskMap.values().forEach(iBaseParallelTask -> {
            taskMap.put(iBaseParallelTask.getParallelTaskKey().getKey(), iBaseParallelTask);
        });
        // 用lamb表达式看看是否能实现
        List<IBaseParallelTask> resultList = new ArrayList<>();
        for (ParallelTaskKey taskKey : keys) {
            if (taskKey == null || taskKey.getKey() == null) {
                continue;
            }
            resultList.add(taskMap.get(taskKey.getKey()));
        }
        return resultList;
    }
}
