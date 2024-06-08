package jrouterfx.reactive;
import jrouterfx.hooks.*;
import lombok.Getter;
import java.util.*;

public class Signal<T> implements Observer {

    public enum SignalType{
        onSubscribe,
        onGet,
        onChange,
        onRequest
    };

    public interface SignalCall<T>{
        void run(Signal<T> sign);
    };

    @Getter
    private State<T> _internalState = StatesHook.useState();
    private Map<SignalType, ArrayList<SignalCall<T>> > _calls = new HashMap<>(){{
        put(SignalType.onSubscribe, new ArrayList<SignalCall<T>>());
        put(SignalType.onGet, new ArrayList<SignalCall<T>>());
        put(SignalType.onChange, new ArrayList<SignalCall<T>>());
        put(SignalType.onRequest, new ArrayList<SignalCall<T>>());
    }};
    
    public Signal(T value){
        _internalState = StatesHook.useState(value,this);
    }

    public void listen(SignalType type, SignalCall<T> call){ 
        for(SignalCall<T> c: _calls.get(SignalType.onSubscribe))
            c.run(this);
        _calls.get(type).add(call);
    }

    public void onSubscribe(SignalCall<T> call)
    { listen(SignalType.onSubscribe, call); }
    public void onChange(SignalCall<T> call)
    { listen(SignalType.onChange, call); }
    public void onGet(SignalCall<T> call)
    { listen(SignalType.onGet, call); }
    public void onRequest(SignalCall<T> call)
    { listen(SignalType.onRequest, call); }


    public void unlisten(SignalType type, SignalCall<T> call)
    { _calls.get(type).remove(call); }

    public void unSubscribe(SignalCall<T> call)
    { unlisten(SignalType.onSubscribe, call); }
    public void unChange(SignalCall<T> call)
    { unlisten(SignalType.onChange, call); }
    public void unGet(SignalCall<T> call)
    { unlisten(SignalType.onGet, call); }
    public void unRequest(SignalCall<T> call)
    { unlisten(SignalType.onRequest, call); }

    @Override
    public void update() {
        for(SignalCall<T> call: _calls.get(SignalType.onChange))
            call.run(this);
    }

    public T get(){
        T value = _internalState.get();
        for(SignalCall<T> call: _calls.get(SignalType.onGet))
            call.run(this);
        return value;
    }

    public void set(T value){ _internalState.set(value); }

    public T request(T newValue){
        _internalState.set(newValue);
        for(SignalCall<T> call: _calls.get(SignalType.onRequest))
            call.run(this);
        return get();
    }

}
