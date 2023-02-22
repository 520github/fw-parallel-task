多任务并发执行框架
======================

#### 前言
* 采用CompletionService方式，解决FutureTask.get任务阻塞问题。
* 提供简单、方便的任务定义接口和任务执行入口。
* 能够灵活对任务key、任务业务参数、任务执行结果进行自定义扩展。
* 另外能够支持异常任务重试机制。


#### 使用例子

~~~~
ParallelTaskExecuteServiceFactory
  .getSimpleParallelTaskExecuteServiceInstance()
    .executeParallelTask(
         new BaseParallelRequest(),
          Arrays.asList(DemoParallelTaskKey.DemoEmptyTask, DemoParallelTaskKey.DemoSleepTask,DemoParallelTaskKey.DemoBizFailTask)
          );
~~~~

具体参考单元测试类
com.panda.parallel.demo.DemoParallelTaskTest
