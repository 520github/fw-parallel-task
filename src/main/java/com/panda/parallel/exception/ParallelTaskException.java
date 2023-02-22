package com.panda.parallel.exception;

import com.panda.parallel.key.ParallelTaskKey;

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
