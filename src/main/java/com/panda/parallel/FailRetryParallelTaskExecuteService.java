package com.panda.parallel;

import com.panda.parallel.exception.ParallelTaskException;
import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;
import com.panda.parallel.parameter.FailRetryParallelRequest;
import com.panda.parallel.task.IBaseParallelTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;

public class FailRetryParallelTaskExecuteService extends AbstractParallelTaskExecuteService {
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
        // TODO 需要根据重试的次数及任务数，做最大循环次数限制，避免由于某种原因导致的死循环
        while (parallelTaskList.size() != responseList.size()) {
            BaseParallelResponse response = pollResult(parallelTaskList, completionService, request);
            if (response != null) {
                responseList.add(response);
            }
        }
        return responseList;
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
            BaseParallelResponse response = responseFuture.get();
            if (response.getException() == null) {
                return response;
            }
            FailRetryParallelRequest failRetryRequest = getFailRetryParallelRequest(request);
            // 失败不做重试，直接返回异常结果
            if (failRetryRequest.getFailRetryNum() < 1) {
                return response;
            }

            IBaseParallelTask task = getBaseParallelTaskByKey(response.getParallelTaskKey(), parallelTaskList);
            String taskKey = getTaskKey(task);
            // 已经超过设置的重试次数
            if (!failRetryRequest.doRetryByTaskKey(taskKey)) {
                response.setRetryNum(failRetryRequest.getRetryNumByTaskKey(taskKey));
                return response;
            }

            // 进行任务重试处理
            failRetry(completionService, request, task);
            return null;
        } catch (Exception e) {
            return BaseParallelResponse.newExceptionResponse(ParallelTaskException.create(e));
        }
    }

    private FailRetryParallelRequest getFailRetryParallelRequest(BaseParallelRequest request) {
        return (FailRetryParallelRequest) request;
    }

    /**
     * 失败重试
     * 
     * @param completionService
     * @param request
     * @param task
     */
    private void failRetry(CompletionService completionService, BaseParallelRequest request, IBaseParallelTask task) {
        // 重新提交任务再次重试
        if (task == null) {
            return;
        }
        submitTask(completionService, task, request);
        getFailRetryParallelRequest(request).incRetryNumByTaskKey(getTaskKey(task));
    }

    /**
     * 获取任务key
     * 
     * @param task
     * @return
     */
    private String getTaskKey(IBaseParallelTask task) {
        return task.getParallelTaskKey().getKey();
    }

    /**
     * 根据任务key获取任务对象
     * 
     * @param key
     * @param parallelTaskList
     * @return
     */
    protected IBaseParallelTask getBaseParallelTaskByKey(ParallelTaskKey key,
            List<IBaseParallelTask> parallelTaskList) {
        return parallelTaskList.stream().filter(iBaseParallelTask -> key == iBaseParallelTask.getParallelTaskKey())
                .findFirst().orElse(null);
    }
}
