package jrouterfx.hooks;
import jrouterfx.reactive.*;


/**
 * Class for States Hooks Creations
 */
public class StatesHook {
    
    /**
    * The implemented useState default all (init data on null)
    * @param init the init data for the State
    * @return The State Object
    */
    public static <T>State<T> useState()
    { return useState(null); }

    /**
    * The implemented useState
    * @param init the init data for the State
    * @return The State Object
    */
    public static <T>State<T> useState(T init)
    { return new State<T>(init); }

    /**
    * The fullest implemented useState
    * @param init the init data for the State
    * @param obj Observer to subscribe to state
    * @return The State Object
    */
    public static <T,O extends Observer>State<T> useState(T init, O obj){
        State<T> state = useState(init);
        state.subscribe(obj);
        return state;
    }

}
