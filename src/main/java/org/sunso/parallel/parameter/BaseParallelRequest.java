package org.sunso.parallel.parameter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 并发任务基础请求参数
 * 
 * @param <B>
 */
public class BaseParallelRequest<B> {
    /**
     * 任务所需的业务参数
     */
    private B request;

    /**
     * 线程池执行器
     */
    private ExecutorService executor;
    /**
     * 每次获取结果的等待超时时间
     */
    private int poolTaskResultTimeout = 1;
    /**
     * 每次获取结果的等待超时时间对应单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 任务bean是否由spring进行管理
     */
    private boolean isSpringTaskBean = false;

    public static BaseParallelRequest create() {
        return new BaseParallelRequest();
    }

    public BaseParallelRequest() {
    }

    public BaseParallelRequest(B request) {
        this.request = request;
    }

    public BaseParallelRequest(B request, ExecutorService executor) {
        this.request = request;
        this.executor = executor;
    }

    public B getRequest() {
        return request;
    }

    public void setRequest(B request) {
        this.request = request;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public int getPoolTaskResultTimeout() {
        return poolTaskResultTimeout;
    }

    public void setPoolTaskResultTimeout(int poolTaskResultTimeout) {
        this.poolTaskResultTimeout = poolTaskResultTimeout;
    }

    public boolean isSpringTaskBean() {
        return isSpringTaskBean;
    }

    public BaseParallelRequest<B> setSpringTaskBean(boolean springTaskBean) {
        isSpringTaskBean = springTaskBean;
        return this;
    }

    public FailRetryParallelRequest convertToFailRetryParallelRequest() {
        return (FailRetryParallelRequest) this;
    }
}
