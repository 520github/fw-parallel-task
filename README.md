多任务并发执行框架
======================

#### 前言
* 采用CompletionService方式，解决FutureTask.get任务阻塞问题。
* 提供简单、方便的任务定义接口和任务执行入口。
* 能够灵活对任务key、任务业务参数、任务执行结果进行自定义扩展。
* 另外能够支持异常任务重试机制。


#### 使用例子
###### 非spring注入方式
* 定义任务key枚举
~~~~
package org.sunso.parallel.demo.key;

import org.sunso.parallel.demo.task.DemoBizFailParallelTask;
import org.sunso.parallel.demo.task.DemoEmptyParallelTask;
import org.sunso.parallel.demo.task.DemoSleepParallelTask;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.task.IBaseParallelTask;

public enum DemoParallelTaskKey implements ParallelTaskKey {
    DemoEmptyTask("demoEmptyTask", new DemoEmptyParallelTask()), 
    DemoSleepTask("demoSleepTask", new DemoSleepParallelTask()), 
    DemoBizFailTask("demoBizFailTask", new DemoBizFailParallelTask()),;

    private String key;
    private IBaseParallelTask iBaseParallelTask;

    DemoParallelTaskKey(String key) {
        this.key = key;
    }

    DemoParallelTaskKey(String key, IBaseParallelTask iBaseParallelTask) {
        this.iBaseParallelTask = iBaseParallelTask;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public IBaseParallelTask getIBaseParallelTask() {
        return iBaseParallelTask;
    }
}

~~~~

* 定义相关任务
~~~~
package org.sunso.parallel.demo.task;

import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.demo.parameter.DemoBizResult;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

public class DemoBizFailParallelTask implements IBaseParallelTask<DemoBizParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return DemoParallelTaskKey.DemoBizFailTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<DemoBizParameter> request) throws Exception {
        DemoBizResult bizResult = DemoBizResult.newInstance().setName(request.getRequest().getRequest())
                .setTitle("gold");
        return BaseParallelResponse.newBizFailResponse(getParallelTaskKey(), bizResult);
    }
}
~~~~
~~~~
package org.sunso.parallel.demo.task;

import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.demo.parameter.DemoParallelResponse;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

public class DemoEmptyParallelTask implements IBaseParallelTask<DemoBizParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return DemoParallelTaskKey.DemoEmptyTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<DemoBizParameter> request) {
        int result = 1 / 0;
        return DemoParallelResponse.newBizSuccessResponse(getParallelTaskKey(),
                request.getRequest().getRequest() + ":empty");
    }
}
~~~~
~~~~
package org.sunso.parallel.demo.task;

import org.sunso.parallel.demo.key.DemoParallelTaskKey;
import org.sunso.parallel.demo.parameter.DemoBizParameter;
import org.sunso.parallel.demo.parameter.DemoBizResult;
import org.sunso.parallel.demo.parameter.DemoParallelResponse;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

public class DemoSleepParallelTask implements IBaseParallelTask<DemoBizParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return DemoParallelTaskKey.DemoSleepTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<DemoBizParameter> request) throws Exception {
        Thread.sleep(15000);
        DemoBizResult bizResult = DemoBizResult.newInstance().setName(request.getRequest().getRequest())
                .setTitle("gold");
        return DemoParallelResponse.newResponse(getParallelTaskKey(), bizResult);
    }
}
~~~~
*  调用并行框架执行任务
~~~~
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
                .executeParallelTask(parallelTaskKeyList);

        // 验证结果
        checkReponseList(responseList, parallelTaskKeyList);
        // 打印结果
        printResponseAboutNum(responseList);
    }
~~~~

###### spring注入方式
* 定义业务请求参数和业务返回结果
~~~~
package org.sunso.demo.index.parameter;

public class IndexTaskParameter {
    public static IndexTaskParameter create() {
        return new IndexTaskParameter();
    }
}
~~~~
~~~~
package org.sunso.demo.index.parameter;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelResponse;

public class IndexMysqlParallelResponse extends BaseParallelResponse<IndexMysqlParallelResponse.MysqlData> {

    public static IndexMysqlParallelResponse newBizSuccessResponse(ParallelTaskKey parallelTaskKey, IndexMysqlParallelResponse.MysqlData mysqlData) {
        IndexMysqlParallelResponse response = new IndexMysqlParallelResponse();
        response.setBizSuccess(true);
        response.setParallelTaskKey(parallelTaskKey);
        response.setData(mysqlData);
        return response;
    }

    public static class MysqlData {

        public static MysqlData create() {
            return new MysqlData();
        }

        private String mysqlData;

        public String getMysqlData() {
            return mysqlData;
        }

        public MysqlData setMysqlData(String mysqlData) {
            this.mysqlData = mysqlData;
            return this;
        }
    }
}
~~~~


* 定义任务key枚举
~~~~
package org.sunso.demo.index;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.task.IBaseParallelTask;

public enum IndexParallelTaskKey implements ParallelTaskKey {
    IndexReadMysqlTask("IndexReadMysqlTask"),
    IndexReadRedisTask("IndexReadRedisTask"),
    IndexReadS3Task("IndexReadS3Task")
    ;

    private String key;
    IndexParallelTaskKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public IBaseParallelTask getIBaseParallelTask() {
        return null;
    }
}

~~~~

* 定义相关任务
~~~~
package org.sunso.demo.index.task;

import org.springframework.stereotype.Component;
import org.sunso.demo.index.IndexParallelTaskKey;
import org.sunso.demo.index.parameter.IndexMysqlParallelResponse;
import org.sunso.demo.index.parameter.IndexTaskParameter;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

@Component
public class IndexReadMysqlTask implements IBaseParallelTask<IndexTaskParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return IndexParallelTaskKey.IndexReadMysqlTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<IndexTaskParameter> baseParallelRequest) throws Exception {
        return IndexMysqlParallelResponse.newBizSuccessResponse(getParallelTaskKey(), IndexMysqlParallelResponse.MysqlData.create().setMysqlData("mysqlData"));
    }
}
~~~~

~~~~
package org.sunso.demo.index.task;

import org.springframework.stereotype.Component;
import org.sunso.demo.index.IndexParallelTaskKey;
import org.sunso.demo.index.parameter.IndexTaskParameter;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

@Component
public class IndexReadRedisTask implements IBaseParallelTask<IndexTaskParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return IndexParallelTaskKey.IndexReadRedisTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<IndexTaskParameter> baseParallelRequest) throws Exception {
        return BaseParallelResponse.newBizSuccessResponse(getParallelTaskKey(), "redis");
    }
}
~~~~

~~~~
package org.sunso.demo.index.task;

import org.springframework.stereotype.Component;
import org.sunso.demo.index.IndexParallelTaskKey;
import org.sunso.demo.index.parameter.IndexTaskParameter;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

@Component
public class IndexReadS3Task implements IBaseParallelTask<IndexTaskParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return IndexParallelTaskKey.IndexReadS3Task;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<IndexTaskParameter> baseParallelRequest) throws Exception {
        return BaseParallelResponse.newBizSuccessResponse(getParallelTaskKey(), "s3");
    }
}
~~~~

* 调用并行框架执行任务
~~~~
package org.sunso.demo.index;

import org.springframework.stereotype.Service;
import org.sunso.demo.index.parameter.IndexTaskParameter;
import org.sunso.parallel.bootstrap.FailRetryParallelTaskBootstrap;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class IndexService {
    public void runIndexParallelTask(IndexTaskParameter indexTaskParameter) {
        List<BaseParallelResponse> responseList =  FailRetryParallelTaskBootstrap.create()
                .setFailRetryNum(3)
                .setExecutor(Executors.newFixedThreadPool(3))
                .setPoolTaskResultTimeout(100)
                .setTimeUnit(TimeUnit.MILLISECONDS)
                .setBizRequest(indexTaskParameter)
                .isSpringTaskBean(true)  //spring注入方式关键配置
                .executeParallelTask(
                        IndexParallelTaskKey.IndexReadMysqlTask,
                        IndexParallelTaskKey.IndexReadRedisTask,
                        IndexParallelTaskKey.IndexReadS3Task
                );
        System.out.println("size:"+ responseList.size());
    }
}
~~~~

#### 具体参考单元测试类
~~~~
org.sunso.parallel.demo.DemoFailRetryParallelTaskTest
org.sunso.parallel.demo.DemoSimpleParallelTaskTest
~~~~

