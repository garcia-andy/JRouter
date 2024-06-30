package jrouterfx.Apps;
import jrouterfx.JRouter;
import jrouterfx.GuiProviders.GuiProvider;
import jrouterfx.Loaders.MainLoader;

/**
 * Absolute Loader Provider
 */
public class AbsoluteLoader <SceneT> implements ApplicationProvider<SceneT>{
     /**
     * Use the Absolute Loader 
     */
    @Override
    public MainLoader<SceneT> provide(GuiProvider<SceneT> prov) 
    { return JRouter.Hooks.Loaders.useLoader(prov); }
}
