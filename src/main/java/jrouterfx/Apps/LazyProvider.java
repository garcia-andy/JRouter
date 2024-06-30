package jrouterfx.Apps;

import jrouterfx.JRouter;
import jrouterfx.GuiProviders.GuiProvider;
import jrouterfx.Loaders.MainLoader;

/**
 * Lazy Loader Provider
 */
public class LazyProvider<SceneT> implements ApplicationProvider<SceneT> {
    /**
     * Use the Lazy Loader 
     */
    @Override
    public MainLoader<SceneT> provide(GuiProvider<SceneT> prov) 
    { return JRouter.Hooks.Loaders.useLazyLoader(prov); }
}
