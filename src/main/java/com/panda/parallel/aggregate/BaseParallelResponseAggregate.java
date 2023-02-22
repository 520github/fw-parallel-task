package com.panda.parallel.aggregate;

import com.panda.parallel.parameter.BaseParallelResponse;
import org.springframework.util.Assert;

import java.util.List;

public class BaseParallelResponseAggregate {

    public static BaseParallelResponseAggregate newInstance(List<BaseParallelResponse> responseList) {
        return new BaseParallelResponseAggregate(responseList);
    }

    private List<BaseParallelResponse> responseList;

    public BaseParallelResponseAggregate(List<BaseParallelResponse> responseList) {
        Assert.notNull(responseList, "responseList is not null");
        this.responseList = responseList;
    }

    public boolean isAllSuccess() {
        return responseList.stream().filter(response -> !response.isBizSuccess()).findFirst().orElse(null) == null;
    }

    public long bizSuccessNum() {
        return responseList.stream().filter(response -> response.isBizSuccess()).count();
    }

    public long bizFailNum() {
        return responseList.stream().filter(response -> !response.isBizSuccess() && response.getException() == null)
                .count();
    }

    public long exceptionNum() {
        return responseList.stream().filter(response -> response.getException() != null).count();
    }

    public long retryTaskNum() {
        return responseList.stream().filter(response -> response.getRetryNum() > 0).count();
    }

    public long retryTaskTotalNum() {
        return responseList.stream().filter(response -> response.getRetryNum() > 0).mapToLong(res -> res.getRetryNum())
                .sum();
    }
}
