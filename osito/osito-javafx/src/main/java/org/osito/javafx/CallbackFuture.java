package org.osito.javafx;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javafx.util.Callback;

public class CallbackFuture<R> extends FutureTask<R> implements Callback<R, R> {

    private MyCallable<R> callable;
    
    private CallbackFuture(MyCallable<R> callable) {
        super(callable);
        this.callable = callable;
    }
    
    @Override
    public R get() {
        try {
            return super.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    public static <R> CallbackFuture<R> callbackFuture() {
        return new CallbackFuture<R>(new MyCallable<R>());
    }

    @Override
    public R call(R result) {
        callable.setResult(result);
        run();
        return result;
    }
    
    private static class MyCallable<R> implements Callable<R> {

        private R result;
        
        public void setResult(R result) {
            this.result = result;
        }
        
        @Override
        public R call() throws Exception {
            return result;
        }
        
    }
}
