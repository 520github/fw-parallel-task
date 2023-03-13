package org.sunso.parallel;

import org.sunso.parallel.exception.ParallelTaskException;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;
import org.sunso.parallel.task.ParallelTaskCall;
import org.sunso.parallel.task.chain.IBaseParallelTaskChain;
import org.sunso.parallel.task.chain.ParallelTaskChainFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public abstract class AbstractParallelTaskExecuteService implements ParallelTaskExecuteServiceI {
    public List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request,
            ParallelTaskKey... parallelTaskKeys) {
        return executeParallelTask(request, Arrays.asList(parallelTaskKeys));
    }

    public List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request,
            List<ParallelTaskKey> taskKeyList) {
        Assert.notNull(request, "you must set [request] parameter");
        Assert.notNull(request.getExecutor(), "you must set [executor in request] parameter");
        Assert.notEmpty(taskKeyList, "you must set [taskKeyList] parameter");
        return executeParallelTask(request, taskKeyList, getAutoIBaseParallelTaskChain(request));
    }

    protected IBaseParallelTaskChain getAutoIBaseParallelTaskChain(BaseParallelRequest request) {
        return ParallelTaskChainFactory.getAutoParallelTaskChain(request);
    }

    /**
     * 提交任务并发执行并获取结果列表
     * 
     * @param request
     * @param taskKeyList
     * @param taskChain
     * @return
     */
    private List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request,
            List<ParallelTaskKey> taskKeyList, IBaseParallelTaskChain taskChain) {
        List<IBaseParallelTask> parallelTaskList = taskChain.getParallelTaskList(taskKeyList);
        return executeParallelTask(parallelTaskList, request);
    }

    public List<BaseParallelResponse> executeParallelTask(List<IBaseParallelTask> parallelTaskList, BaseParallelRequest request) {
        CompletionService completionService = getCompletionService(getExecutorService(request));
        parallelTaskList.forEach(parallelTask -> {
            submitTask(completionService, parallelTask, request);
        });

        return getResponseList(parallelTaskList, completionService, request);
    }

    /**
     * 提交任务
     * 
     * @param completionService
     * @param parallelTask
     * @param request
     */
    protected void submitTask(CompletionService completionService, IBaseParallelTask parallelTask,
            BaseParallelRequest request) {
        completionService.submit(new ParallelTaskCall(request, parallelTask));
    }

    /**
     * 获取任务结果列表
     * 
     * @param parallelTaskList
     * @param completionService
     * @param request
     * @return
     */
    protected List<BaseParallelResponse> getResponseList(List<IBaseParallelTask> parallelTaskList,
            CompletionService completionService, BaseParallelRequest request) {
        List<BaseParallelResponse> responseList = new ArrayList<>();
        parallelTaskList.forEach(parallelTask -> {
            responseList.add(pollResult(parallelTaskList, completionService, request));
        });
        return responseList;
    }

    /**
     * 获取线程执行器
     * 
     * @param request
     * @return
     */
    private ExecutorService getExecutorService(BaseParallelRequest request) {
        if (request.getExecutor() == null) {
            throw new NullPointerException("you must set executor parameter");
        }
        return request.getExecutor();

    }

    /**
     * 获取已执行完任务的结果
     * 
     * @param completionService
     * @param request
     * @return
     */
    protected BaseParallelResponse pollResult(List<IBaseParallelTask> parallelTaskList,
            CompletionService completionService, BaseParallelRequest request) {
        try {
            Future<BaseParallelResponse> responseFuture = null;
            while (responseFuture == null) {
                responseFuture = completionService.poll(request.getPoolTaskResultTimeout(), request.getTimeUnit());
            }
            return responseFuture.get();
        } catch (Exception e) {
            return BaseParallelResponse.newExceptionResponse(ParallelTaskException.create(e));
        }
    }

    private CompletionService<BaseParallelResponse> getCompletionService(ExecutorService executor) {
        return new ExecutorCompletionService<>(executor);
    }
}
