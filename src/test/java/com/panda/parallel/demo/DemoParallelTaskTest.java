package com.panda.parallel.demo;

import com.panda.parallel.ParallelTaskExecuteServiceFactory;
import com.panda.parallel.ParallelTaskExecuteServiceI;
import com.panda.parallel.aggregate.BaseParallelResponseAggregate;
import com.panda.parallel.demo.key.DemoParallelTaskKey;
import com.panda.parallel.demo.parameter.DemoBizParameter;
import com.panda.parallel.key.ParallelTaskKey;
import com.panda.parallel.parameter.BaseParallelRequest;
import com.panda.parallel.parameter.BaseParallelResponse;
import com.panda.parallel.parameter.FailRetryParallelRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoParallelTaskTest {

    /**
     * 任务执行失败不做重试的并行任务测试
     */
    @Test
    public void executeParallelTaskDemoTest() {
        List<ParallelTaskKey> parallelTaskKeyList = getParallelTaskKeyList();
        List<BaseParallelResponse> responseList = getParallelTaskExecuteService()
                .executeParallelTask(getBaseParallelRequest(), parallelTaskKeyList);

        // 验证结果
        Assert.assertNotNull(responseList);
        Assert.assertEquals(parallelTaskKeyList.size(), responseList.size());
        responseList.forEach(baseParallelResponse -> {
            ParallelTaskKey parallelTaskKey = baseParallelResponse.getParallelTaskKey();
            if (!parallelTaskKeyList.contains(parallelTaskKey)) {
                Assert.fail("not found parallelTaskKey:" + parallelTaskKey);
            }
        });
    }

    /**
     * 任务执行失败可以进行重试的并行任务测试
     */
    @Test
    public void executeParallelTaskDemoTestFailRetry() {
        List<ParallelTaskKey> parallelTaskKeyList = getParallelTaskKeyList();
        List<BaseParallelResponse> responseList = ParallelTaskExecuteServiceFactory
                .getFailRetryParallelTaskExecuteServiceInstance()
                .executeParallelTask(getFailRetryParallelRequest(), parallelTaskKeyList);
        Assert.assertNotNull(responseList);
        Assert.assertEquals(parallelTaskKeyList.size(), responseList.size());
        responseList.forEach(baseParallelResponse -> {
            if (baseParallelResponse.getException() != null) {
                Assert.assertEquals(getFailRetryNum(), baseParallelResponse.getRetryNum());
            }
        });

        BaseParallelResponseAggregate responseAggregate = BaseParallelResponseAggregate.newInstance(responseList);
        print(responseAggregate.isAllSuccess());
        print(responseAggregate.bizFailNum());
        print(responseAggregate.bizSuccessNum());
        print(responseAggregate.exceptionNum());
        print(responseAggregate.retryTaskNum());
        print(responseAggregate.retryTaskTotalNum());
    }

    /**
     * 获取任务并发执行器
     * 
     * @return
     */
    private ParallelTaskExecuteServiceI getParallelTaskExecuteService() {
        return ParallelTaskExecuteServiceFactory.getSimpleParallelTaskExecuteServiceInstance();
    }

    public BaseParallelRequest getFailRetryParallelRequest() {
        FailRetryParallelRequest request = new FailRetryParallelRequest(getDemoBizParameter());
        request.setExecutor(getExecutorService());
        request.setFailRetryNum(getFailRetryNum());
        return request;
    }

    private int getFailRetryNum() {
        return 3;
    }

    /**
     * 任务的入口参数
     * 
     * @return
     */
    private BaseParallelRequest getBaseParallelRequest() {
        BaseParallelRequest request = getDemoParallelRequest();
        request.setExecutor(getExecutorService());
        return request;
    }

    private BaseParallelRequest getDemoParallelRequest() {
        // return new DemoParallelRequest(getDemoBizParameter());
        return new BaseParallelRequest(getDemoBizParameter());
    }

    /**
     * 任务涉及的业务参数
     * 
     * @return
     */
    private DemoBizParameter getDemoBizParameter() {
        return DemoBizParameter.newInstance().setRequest("demo");
    }

    /**
     * 任务列表
     * 
     * @return
     */
    private List<ParallelTaskKey> getParallelTaskKeyList() {
        return Arrays.asList(DemoParallelTaskKey.DemoEmptyTask, DemoParallelTaskKey.DemoSleepTask,
                DemoParallelTaskKey.DemoBizFailTask);
    }

    /**
     * 线程池执行器
     * 
     * @return
     */
    private ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(5);
    }

    private void print(Object obj) {
        System.out.println("obj-->" + obj);
    }

}
