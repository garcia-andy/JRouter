package io.github.andy030124.hooks;

import io.github.andy030124.reactive.*;
import java.util.ArrayList;

public class SignalHook {

    class SignalDescriptor<T>{
        public Signal.SignalType type; 
        public Signal.SignalCall<T> call;
    }

public static <T>Signal<T> useSignal(
    T init,
    Signal.SignalCall<T> onChange,
    Signal.SignalCall<T> onRequest,
    Signal.SignalCall<T> onSubscribe,
    Signal.SignalCall<T> onGet
){
    Signal<T> sign = new Signal<T>(init);
    
    if( onChange    != null ) sign.onChange(onChange);
    if( onRequest   != null ) sign.onRequest(onRequest);
    if( onSubscribe != null ) sign.onSubscribe(onSubscribe);
    if( onGet       != null ) sign.onGet(onGet);

    return sign;
}

public static <T>Signal<T> useSignal()
{ return useSignal(null); }

public static <T>Signal<T> useSignal(T init)
{ return useSignal(init, null, null, null, null); }

public static <T>Signal<T> useSignal(
    T init, 
    Signal.SignalType type, 
    Signal.SignalCall<T> call
){
    Signal<T> sign = useSignal(init);
    sign.listen(type, call);
    return sign;
}

public static <T>Signal<T> useSignal(
    T init, 
    ArrayList<SignalDescriptor<T>> signals
){
    Signal<T> sign = useSignal(init);
    for(
        SignalDescriptor<T> signal:
        signals
    ) sign.listen(signal.type, signal.call);
    return sign;
}


}
