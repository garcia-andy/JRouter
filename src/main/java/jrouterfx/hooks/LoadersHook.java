package jrouterfx.hooks;

import jrouterfx.Loaders.*;
import jrouterfx.GuiProviders.*;

/**
 * Class with Hooks for Loaders creations
 */
public class LoadersHook {
    /**
     * Hook for normal loader creation (absolute loader)
     * @param c Class for load working directory
     * @param projectName name of the project to load
     * @param routesFolder routes folder of parse (relative to project folder)
     * @param prov The GUI Provider
     * @return The object Loader
     */
    public static <SceneType> Loader<SceneType> useLoader(
        Class<?> c,
        String projectName, 
        String routesFolder, 
        InterfaceGuiProvider<SceneType> prov
    ){ return new Loader<SceneType>(c,projectName, routesFolder, prov); }

    /**
     * Hook for loader creation (absolute loader)
     * @param prov The GUI Provider
     * @return The object Loader
     */
    public static <SceneType> Loader<SceneType> useLoader(InterfaceGuiProvider<SceneType> prov)
    { return new Loader<SceneType>(prov); }

    /**
     * Hook for laxy loader creation (dynamic requests)
     * @param c Class for load working directory
     * @param projectName name of the project to load
     * @param routesFolder routes folder of parse (relative to project folder)
     * @param prov The GUI Provider
     * @return The object LazyLoader
     */
    public static <SceneType> LazyLoader<SceneType> useLazyLoader(
        Class<?> c,
        String projectName, 
        String routesFolder, 
        InterfaceGuiProvider<SceneType> prov
    ){ return new LazyLoader<SceneType>(c,projectName, routesFolder, prov); }

    /**
     * Hook for laxy loader creation (dynamic requests)
     * @param prov The GUI Provider
     * @return The object LazyLoader
     */
    public static <SceneType> LazyLoader<SceneType> useLazyLoader(InterfaceGuiProvider<SceneType> prov)
    { return new LazyLoader<SceneType>(prov); }

}
