package org.sunso.parallel.parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 并发任务失败重试的请求参数
 * 
 * @param <B>
 */
public class FailRetryParallelRequest<B> extends BaseParallelRequest {
    Map<String, Integer> retryNumMap = new HashMap<>();

    /**
     * 任务失败允许重试的次数
     */
    private int failRetryNum = 0;

    public static FailRetryParallelRequest create() {
        return new FailRetryParallelRequest();
    }

    public FailRetryParallelRequest() {
    }

    public FailRetryParallelRequest(B request) {
        super(request);
    }

    public FailRetryParallelRequest(B request, ExecutorService executor) {
        super(request, executor);
    }

    public int getFailRetryNum() {
        return failRetryNum;
    }

    public void setFailRetryNum(int failRetryNum) {
        this.failRetryNum = failRetryNum;
    }

    public boolean doRetryByTaskKey(String taskKey) {
        if (failRetryNum < 1) {
            return false;
        }
        if (getRetryNumByTaskKey(taskKey) >= failRetryNum) {
            return false;
        }
        return true;
    }

    public int getRetryNumByTaskKey(String taskKey) {
        int value = 0;
        if (retryNumMap.containsKey(taskKey)) {
            value = retryNumMap.get(taskKey).intValue();
        }
        return value;
    }

    public void incRetryNumByTaskKey(String taskKey) {
        int value = 0;
        if (retryNumMap.containsKey(taskKey)) {
            value = retryNumMap.get(taskKey).intValue() + 1;
        }
        retryNumMap.put(taskKey, value);
    }
}
