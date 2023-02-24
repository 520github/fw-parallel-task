package org.sunso.parallel.task.chain;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.task.IBaseParallelTask;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通过spring容器获取对应的任务列表
 */
@Component
public class ApplicationContextParallelTaskChain implements IBaseParallelTaskChain, ApplicationContextAware {
    private Map<String, IBaseParallelTask> taskMap;
    ApplicationContext applicationContext;

    @Override
    public List<IBaseParallelTask> getParallelTaskList(List<ParallelTaskKey> keys) {
        return getParallelTaskListInner(keys);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        taskMap = applicationContext.getBeansOfType(IBaseParallelTask.class);

    }

    /**
     * 根据并发任务key获取对应的任务列表
     * 
     * @param keys
     * @return
     */
    private List<IBaseParallelTask> getParallelTaskListInner(List<ParallelTaskKey> keys) {
        List<IBaseParallelTask> resultList = new ArrayList<>();
        taskMap.values().forEach(iBaseParallelTask -> {
            if (keys.contains(iBaseParallelTask.getParallelTaskKey())) {
                resultList.add(iBaseParallelTask);
            }
        });
        return resultList;
    }
}
