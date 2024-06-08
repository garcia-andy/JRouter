package jrouterfx;
import jrouterfx.hooks.*;

/**
 * Global Class for access to Hooks and all Apis
*/
public class JRouterFX {
    /**
     * !Unused
     */
    public JRouterFX(){}

    /**
     * Class for wrapper of hooks
     */
    public static class Hooks{
        /**
        * !Unused
        */
        public Hooks(){}

        /**
         * Class with the useState apis
         */
        public static class State extends StatesHook{
            /**
            * !Unused
            */
            public State(){}
        }
        /**
         * Class with the useSignal apis
         */
        public static class Signal extends SignalHook{
            /**
             * !Unused
            */
            public Signal(){}
        }
    }

    /**
     * Class for wrapper of hooks
     */
    public static class Loaders extends LoadersHook {
        /**
        * !Unused
        */
        public Loaders(){}
    }

    
}
