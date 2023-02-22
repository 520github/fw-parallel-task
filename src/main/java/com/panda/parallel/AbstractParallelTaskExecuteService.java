package com.panda.parallel;

import com.panda.parallel.exception.ParallelTaskException;
import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;
import com.panda.parallel.task.IBaseParallelTask;
import com.panda.parallel.task.ParallelTaskCall;
import com.panda.parallel.task.chain.IBaseParallelTaskChain;
import com.panda.parallel.task.chain.ParallelTaskChainFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public abstract class AbstractParallelTaskExecuteService implements ParallelTaskExecuteServiceI {

    public List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request,
            ParallelTaskKey... parallelTaskKeys) {
        return executeParallelTask(request, Arrays.asList(parallelTaskKeys), getIBaseParallelTaskChain());
    }

    public List<BaseParallelResponse> executeParallelTask(BaseParallelRequest request,
            List<ParallelTaskKey> taskKeyList) {
        Assert.notNull(request, "you must set [request] parameter");
        Assert.notNull(request.getExecutor(), "you must set [executor in request] parameter");
        Assert.notEmpty(taskKeyList, "you must set [taskKeyList] parameter");
        return executeParallelTask(request, taskKeyList, getIBaseParallelTaskChain());
    }

    protected IBaseParallelTaskChain getIBaseParallelTaskChain() {
        return ParallelTaskChainFactory.getDefaultParallelTaskChain();
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
