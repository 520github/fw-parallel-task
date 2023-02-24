package org.sunso.parallel.parameter;

import org.sunso.parallel.exception.ParallelTaskException;
import org.sunso.parallel.key.ParallelTaskKey;

/**
 * 并发任务基础结果类
 * 
 * @param <R>
 */
public class BaseParallelResponse<R> {
    /**
     * 任务对应的key
     */
    private ParallelTaskKey parallelTaskKey;
    /**
     * 业务处理是否成功
     */
    private boolean bizSuccess = true;
    /**
     * 任务执行返回结果数据
     */
    private R data;

    /**
     * 任务处理异常情况
     */
    private ParallelTaskException exception;

    /**
     * 重试次数
     */
    private int retryNum = 0;

    public BaseParallelResponse() {
    }

    public BaseParallelResponse(R data) {
        this.data = data;
    }

    public ParallelTaskKey getParallelTaskKey() {
        return parallelTaskKey;
    }

    public void setParallelTaskKey(ParallelTaskKey parallelTaskKey) {
        this.parallelTaskKey = parallelTaskKey;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }

    public ParallelTaskException getException() {
        return exception;
    }

    public void setException(ParallelTaskException exception) {
        this.exception = exception;
    }

    public int getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(int retryNum) {
        this.retryNum = retryNum;
    }

    public boolean isBizSuccess() {
        return bizSuccess;
    }

    public void setBizSuccess(boolean bizSuccess) {
        this.bizSuccess = bizSuccess;
    }

    public static BaseParallelResponse newExceptionResponse(ParallelTaskException e) {
        return newExceptionResponse(null, e);
    }

    public static BaseParallelResponse newExceptionResponse(ParallelTaskKey parallelTaskKey, ParallelTaskException e) {
        BaseParallelResponse response = new BaseParallelResponse();
        response.setParallelTaskKey(parallelTaskKey);
        response.setException(e);
        response.setBizSuccess(false);
        response.setData(e.getMessage());
        return response;
    }

    public static BaseParallelResponse newBizSuccessResponse(ParallelTaskKey parallelTaskKey) {
        return newBizSuccessResponse(parallelTaskKey, null);
    }

    public static BaseParallelResponse newBizSuccessResponse(ParallelTaskKey parallelTaskKey, Object data) {
        BaseParallelResponse response = new BaseParallelResponse();
        response.setParallelTaskKey(parallelTaskKey);
        response.setData(data);
        return response;
    }

    public static BaseParallelResponse newBizFailResponse(ParallelTaskKey parallelTaskKey) {
        return newBizFailResponse(parallelTaskKey, null);
    }

    public static BaseParallelResponse newBizFailResponse(ParallelTaskKey parallelTaskKey, Object data) {
        BaseParallelResponse response = new BaseParallelResponse();
        response.setParallelTaskKey(parallelTaskKey);
        response.setData(data);
        response.setBizSuccess(false);
        return response;
    }
}
