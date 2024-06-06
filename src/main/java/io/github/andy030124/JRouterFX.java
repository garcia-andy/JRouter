package io.github.andy030124;
import io.github.andy030124.hooks.*;

/**
 * Global Class for access to Hooks and all Apis
 */
public class JRouterFX {
    /**
     * Class for wrapper of hooks
     */
    public class Hooks{
        /**
         * Class with the useState apis
         */
        public class State extends StatesHook{}
        /**
         * Class with the useSignal apis
         */
        public class Signal extends SignalHook{}
    }

    /**
     * Class for wrapper of hooks
     */
    public class Loaders extends LoadersHook {}
}
