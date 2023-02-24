package org.sunso.parallel.demo;

import org.junit.Assert;
import org.sunso.parallel.aggregate.BaseParallelResponseAggregate;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.List;

public abstract class AbstractParallelTaskTest {
    protected void checkReponseList(List<BaseParallelResponse> responseList,
            List<ParallelTaskKey> parallelTaskKeyList) {
        Assert.assertNotNull(responseList);
        Assert.assertEquals(parallelTaskKeyList.size(), responseList.size());
        responseList.forEach(baseParallelResponse -> {
            ParallelTaskKey parallelTaskKey = baseParallelResponse.getParallelTaskKey();
            if (!parallelTaskKeyList.contains(parallelTaskKey)) {
                Assert.fail("not found parallelTaskKey:" + parallelTaskKey);
            }
        });
    }

    protected void printResponseAboutNum(List<BaseParallelResponse> responseList) {
        BaseParallelResponseAggregate responseAggregate = BaseParallelResponseAggregate.newInstance(responseList);
        print(responseAggregate.isAllSuccess());
        print(responseAggregate.bizFailNum());
        print(responseAggregate.bizSuccessNum());
        print(responseAggregate.exceptionNum());
        print(responseAggregate.retryTaskNum());
        print(responseAggregate.retryTaskTotalNum());
    }

    protected void print(Object obj) {
        System.out.println("obj-->" + obj);
    }
}
