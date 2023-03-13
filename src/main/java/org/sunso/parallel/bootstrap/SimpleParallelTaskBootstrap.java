package org.sunso.parallel.bootstrap;

import org.sunso.parallel.ParallelTaskExecuteServiceFactory;
import org.sunso.parallel.ParallelTaskExecuteServiceI;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 简单并发任务的引导类
 */
public class SimpleParallelTaskBootstrap {

    protected BaseParallelRequest request;

    public static SimpleParallelTaskBootstrap create() {
        return new SimpleParallelTaskBootstrap();
    }

    protected SimpleParallelTaskBootstrap() {
        request = BaseParallelRequest.create();
    }

    /**
     * 设置线程池执行器
     * 
     * @param executor
     * @return
     */
    public SimpleParallelTaskBootstrap setExecutor(ExecutorService executor) {
        request.setExecutor(executor);
        return this;
    }

    /**
     * 设置每次拉取结果的超时时间
     * 
     * @param poolTaskResultTimeout
     * @return
     */
    public SimpleParallelTaskBootstrap setPoolTaskResultTimeout(int poolTaskResultTimeout) {
        request.setPoolTaskResultTimeout(poolTaskResultTimeout);
        return this;
    }

    /**
     * 设置超时时间的单位
     * 
     * @param timeUnit
     * @return
     */
    public SimpleParallelTaskBootstrap setTimeUnit(TimeUnit timeUnit) {
        request.setTimeUnit(timeUnit);
        return this;
    }

    /**
     * 设置业务请求数据
     * 
     * @param bizRequest
     * @return
     */
    public SimpleParallelTaskBootstrap setBizRequest(Object bizRequest) {
        request.setRequest(bizRequest);
        return this;
    }

    /**
     * 任务类是否通过spring注入
     * 
     * @param isSpringTaskBean
     * @return
     */
    public SimpleParallelTaskBootstrap isSpringTaskBean(boolean isSpringTaskBean) {
        request.setSpringTaskBean(isSpringTaskBean);
        return this;
    }

    /**
     * 根据任务key,并发执行任务
     * 
     * @param parallelTaskKeys
     * @return
     */
    public List<BaseParallelResponse> executeParallelTaskKey(ParallelTaskKey... parallelTaskKeys) {
        return getParallelTaskExecuteServiceI().executeParallelTask(request, parallelTaskKeys);
    }

    /**
     * 根据任务key,并发执行任务
     * 
     * @param parallelTaskKeyList
     * @return
     */
    public List<BaseParallelResponse> executeParallelTaskKey(List<ParallelTaskKey> parallelTaskKeyList) {
        return getParallelTaskExecuteServiceI().executeParallelTask(request, parallelTaskKeyList);
    }

    /**
     * 根据任务,并发执行任务
     * @param parallelTaskList
     * @return
     */
    public List<BaseParallelResponse> executeParallelTask(List<IBaseParallelTask> parallelTaskList) {
        return getParallelTaskExecuteServiceI().executeParallelTask(parallelTaskList, request);
    }

    /**
     * 获取并发任务执行器
     * 
     * @return
     */
    protected ParallelTaskExecuteServiceI getParallelTaskExecuteServiceI() {
        return ParallelTaskExecuteServiceFactory.getSimpleParallelTaskExecuteServiceInstance();
    }

}
