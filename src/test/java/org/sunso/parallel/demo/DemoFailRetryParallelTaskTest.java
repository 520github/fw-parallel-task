package org.sunso.parallel.demo;

import org.junit.Test;
import org.sunso.parallel.bootstrap.FailRetryParallelTaskBootstrap;
import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DemoFailRetryParallelTaskTest extends AbstractParallelTaskTest {

    @Test
    public void demoFailRetryParallelTaskTest() {
        List<ParallelTaskKey> parallelTaskKeyList = Arrays.asList(DemoParallelTaskKey.DemoEmptyTask,
                DemoParallelTaskKey.DemoSleepTask, DemoParallelTaskKey.DemoBizFailTask);
        List<BaseParallelResponse> responseList = FailRetryParallelTaskBootstrap.create()
                .setFailRetryNum(3)
                .setExecutor(Executors.newFixedThreadPool(5))
                .setPoolTaskResultTimeout(100)
                .setTimeUnit(TimeUnit.MILLISECONDS)
                .setBizRequest(DemoBizParameter.newInstance().setRequest("demo"))
                .executeParallelTaskKey(parallelTaskKeyList);

        // 验证结果
        checkReponseList(responseList, parallelTaskKeyList);
        // 打印结果
        printResponseAboutNum(responseList);
    }
}
