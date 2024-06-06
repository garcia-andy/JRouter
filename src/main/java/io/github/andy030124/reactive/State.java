package io.github.andy030124.reactive;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class State<T> extends Observable<Observer> {
    private Semaphore _AvaiablSemaphore = new Semaphore(1);
    private Semaphore _mod = new Semaphore(1);
    private Semaphore _updating = new Semaphore(1);

    private ArrayDeque<Future<Void>> _modifiers =  
        new ArrayDeque<Future<Void>>();

    private T _valueState;
    private ArrayDeque<T> _values = new ArrayDeque<T>();

    public State(T initialValue){
        _valueState = initialValue;
    }

    public void set(T value){
        CompletableFuture.runAsync(() -> {
            _AvaiablSemaphore.acquireUninterruptibly();
                _values.push(value);
            _AvaiablSemaphore.release();
        });
        update();
        notifyObservers();
    }

    public T get(){
        update();
        return _valueState;
    }

    
    private void update() {
        if( !_updating.tryAcquire() ) return;

        _mod.acquireUninterruptibly();
            _modifiers.forEach((future) -> {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            while( _values.size() > 0 )
            { _valueState = this._values.pop(); }
        _mod.release();
        _updating.release();
    }
    
}
