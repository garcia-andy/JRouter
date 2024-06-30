package jrouter.Apps;

import jrouter.GuiProviders.GuiProvider;
import jrouter.Loaders.MainLoader;

/**
 * The Interface for All Loader Providers (Application Types)
 */
public interface ApplicationProvider<SceneT> {
    /**
     * Provide the Loader with the GUI Context, and return the generic Loader Interface
     * @param prov The GUI Abstraction Layer
     * @return the generic Loader Interface
     */
    MainLoader<SceneT> provide(GuiProvider<SceneT> prov);
}
