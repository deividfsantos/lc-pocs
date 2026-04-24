package com.dsantos;
import java.util.concurrent.atomic.AtomicReference;
public class TaskResult<T> {
    enum State {
        PENDING, DONE, FAILED
    }
    private final AtomicReference<State> state = new AtomicReference<>(State.PENDING);
    private volatile T value;
    private volatile Exception error;
    void complete(T result) {
        this.value = result;
        state.set(State.DONE);
    }
    void fail(Exception e) {
        this.error = e;
        state.set(State.FAILED);
    }
    public boolean isDone() {
        return state.get() != State.PENDING;
    }
    public T get() {
        // TODO: block until done
        if (state.get() == State.FAILED) {
            throw new RuntimeException(error);
        }
        return value;
    }
}
