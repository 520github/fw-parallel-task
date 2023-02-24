package org.sunso.parallel.exception;

import org.sunso.parallel.key.ParallelTaskKey;

/**
 * 并发任务异常类
 */
public class ParallelTaskException extends Exception {

    public static ParallelTaskException create(ParallelTaskKey taskKey, Exception e) {
        return new ParallelTaskException(taskKey.getKey(), e);
    }

    public static ParallelTaskException create(Exception e) {
        return new ParallelTaskException(e.getMessage(), e);
    }

    public ParallelTaskException(String message) {
        super(message);
    }

    public ParallelTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
