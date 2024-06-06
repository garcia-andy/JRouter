package io.github.andy030124.hooks;
import io.github.andy030124.reactive.*;

public class StatesHook {
    
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

}
