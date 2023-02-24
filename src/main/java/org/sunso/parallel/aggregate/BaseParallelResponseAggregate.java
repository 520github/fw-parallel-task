package org.sunso.parallel.aggregate;

import org.sunso.parallel.parameter.BaseParallelResponse;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 对并行任务结果列表进行聚合处理
 */
public class BaseParallelResponseAggregate {

    public static BaseParallelResponseAggregate newInstance(List<BaseParallelResponse> responseList) {
        return new BaseParallelResponseAggregate(responseList);
    }

    private List<BaseParallelResponse> responseList;

    public BaseParallelResponseAggregate(List<BaseParallelResponse> responseList) {
        Assert.notNull(responseList, "responseList is not null");
        this.responseList = responseList;
    }

    /**
     * 所有任务业务是否全都执行成功
     * 
     * @return
     */
    public boolean isAllSuccess() {
        return responseList.stream().filter(response -> !response.isBizSuccess()).findFirst().orElse(null) == null;
    }

    /**
     * 业务处理成功的任务数
     * 
     * @return
     */
    public long bizSuccessNum() {
        return responseList.stream().filter(response -> response.isBizSuccess()).count();
    }

    /**
     * 业务处理失败的任务数
     * 
     * @return
     */
    public long bizFailNum() {
        return responseList.stream().filter(response -> !response.isBizSuccess() && response.getException() == null)
                .count();
    }

    /**
     * 任务处理异常的任务数
     * 
     * @return
     */
    public long exceptionNum() {
        return responseList.stream().filter(response -> response.getException() != null).count();
    }

    /**
     * 有进行重试处理的任务数
     * 
     * @return
     */
    public long retryTaskNum() {
        return responseList.stream().filter(response -> response.getRetryNum() > 0).count();
    }

    /**
     * 任务重试总次数
     * 
     * @return
     */
    public long retryTaskTotalNum() {
        return responseList.stream().filter(response -> response.getRetryNum() > 0).mapToLong(res -> res.getRetryNum())
                .sum();
    }
}
