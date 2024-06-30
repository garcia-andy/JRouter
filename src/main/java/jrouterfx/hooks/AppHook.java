package jrouterfx.hooks;
import jrouterfx.Apps.*;
import jrouterfx.GuiProviders.GuiProvider;

public class AppHook {
    
    /**
     * Basic App creation with the default Provider
     * @param <SceneT> type of the scene of Backend
     * @param title title of the window
     * @return the constructed app
     */
    public static <SceneT> App<SceneT> useApp(String title)
    { return new App<SceneT>(title); }

    /**
     * Basic App creation with the Lazy Provider
     * @param <SceneT> type of the scene of Backend
     * @param title title of the window
     * @return the constructed app with an lazy provider
     */
    public static <SceneT> App<SceneT> useLazyApp(String title)
    { return new App<SceneT>(title, new LazyProvider<SceneT>()); }

    /**
     * Basic App creation with the default Provider
     * @param <SceneT> type of the scene of Backend
     * @param title title of the window
     * @param provider an implementation of the generic gui provider
     * @return the constructed app
     */
    public static <SceneT> App<SceneT> useApp(
        String title, 
        GuiProvider<SceneT> provider
    ){ 
        App<SceneT> app = useApp(title); 
        app.setGuiProvider(provider);
        return app;
    }

    /**
     * Basic App creation with the Lazy Provider
     * @param <SceneT> type of the scene of Backend
     * @param title title of the window
     * @param provider an implementation of the generic gui provider
     * @return the constructed app with an lazy provider
     */
    public static <SceneT> App<SceneT> useLazyApp(
        String title, 
        GuiProvider<SceneT> provider
    ){ 
        App<SceneT> app = useLazyApp(title);
        app.setGuiProvider(provider);
        return app;
    }


}
