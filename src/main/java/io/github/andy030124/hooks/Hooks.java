package io.github.andy030124.hooks;
import java.util.ArrayList;

import io.github.andy030124.reactive.*;
import io.github.andy030124.reactive.Signal.SignalCall;
import javafx.util.Pair;

public class Hooks {

public static <T>State<T> useState()
{ return useState(null); }

public static <T,O extends Observer>State<T> useState(O obj)
{ return useState(null, obj); }

public static <T>State<T> useState(T init)
{ return new State<T>(init); }

public static <T,O extends Observer>State<T> useState(T init, O obj){
    State<T> state = useState(init);
    state.subscribe(obj);
    return state;
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
    ArrayList<Pair<Signal.SignalType, Signal.SignalCall<T>>> signals
){
    Signal<T> sign = useSignal(init);
    for(
        Pair<Signal.SignalType, Signal.SignalCall<T>> signal:
        signals
    ) sign.listen(signal.getKey(), signal.getValue());
    return sign;
}


}
