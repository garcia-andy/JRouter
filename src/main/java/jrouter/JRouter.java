package jrouter;
import jrouter.hooks.*;

/**
 * Global Class for access to Hooks and all Apis
*/
public class JRouter {
    /**
     * !Unused
     */
    public JRouter(){}

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

        /**
         * Class with the useApp & useLazyApp apis
         */
        public static class App extends AppHook{
            /**
             * !Unused
            */
            public App(){}
        }

        /**
         * Class for wrapper Loaders Hooks
         */
        public static class Loaders extends LoadersHook {
            /**
            * !Unused
            */
            public Loaders(){}
        }
    }


    
}
