package jrouter.hooks;

import java.util.ArrayList;

import jrouter.reactive.*;

/**
 * Class for Signals Hooks Creations
 */
public class SignalHook {

    /**
     * Class with the Signal Information
     */
    class SignalDescriptor<T>{
        /** Signal type */
        public Signal.SignalType type; 
        /** Signal Callback */
        public Signal.SignalCall<T> call;
    }

/**
 * The fullest implemented use signal
 * @param T the type of the internal data
 * @param init the init data for the Signal
 * @param onChange optional onChange callback
 * @param onRequest optional onRequest callback
 * @param onSubscribe optional onSubscribe callback
 * @param onGet optional onGet callback
 * @return The Signal Object
 */
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

/**
* The implemented use signal default (all null)
* @param T the type of the internal data
* @return The Signal Object with null data and not callbacks
*/
public static <T>Signal<T> useSignal()
{ return useSignal(null); }

/**
 * The implemented use signal with only init data
 * @param T the type of the internal data
 * @param init the init data for the Signal
 * @return The Signal Object without callbacks
 */
public static <T>Signal<T> useSignal(T init)
{ return useSignal(init, null, null, null, null); }

/**
 * The implemented use signal with init data and one callback
 * @param T the type of the internal data
 * @param init the init data for the Signal
 * @param type the type of the callback to register
 * @param call the callback to register for type
 * @return The Signal Object
 */
public static <T>Signal<T> useSignal(
    T init, 
    Signal.SignalType type, 
    Signal.SignalCall<T> call
){
    Signal<T> sign = useSignal(init);
    sign.listen(type, call);
    return sign;
}

/**
 * The implemented use signal with init data and 
 * ArrayList of SignalDescriptor for callbacks
 * @param T the type of the internal data
 * @param init the init data for the Signal
 * @param signals an ArrayList of SignalDescriptor for register callbacks
 * @return The Signal Object
 */
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
