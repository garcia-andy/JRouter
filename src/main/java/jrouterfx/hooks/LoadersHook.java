package jrouterfx.hooks;

import jrouterfx.Loaders.*;
import javafx.stage.Stage;

/**
 * Class with Hooks for Loaders creations
 */
public class LoadersHook {
    /**
     * Hook for normal loader creation (absolute loader)
     * @param c Class for load working directory
     * @param projectName name of the project to load
     * @param routesFolder routes folder of parse (relative to project folder)
     * @param mainStage the main stage of the FXML states
     * @return The object Loader
     */
    public static Loader useNormalLoader(Class<?> c,String projectName, String routesFolder, Stage mainStage)
    { return new Loader(c,projectName, routesFolder, mainStage); }

    /**
     * Hook for laxy loader creation (dynamic requests)
     * @param c Class for load working directory
     * @param projectName name of the project to load
     * @param routesFolder routes folder of parse (relative to project folder)
     * @param mainStage the main stage of the FXML states
     * @return The object LazyLoader
     */
    public static LazyLoader useLazyLoader(Class<?> c,String projectName, String routesFolder, Stage mainStage)
    { return new LazyLoader(c,projectName, routesFolder, mainStage); }

}
