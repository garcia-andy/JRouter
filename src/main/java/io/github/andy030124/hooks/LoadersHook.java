package io.github.andy030124.hooks;

import io.github.andy030124.Loaders.*;
import javafx.stage.Stage;

/**
 * Class with Hooks for Loaders creations
 */
public class LoadersHook {
    /**
     * Hook for normal loader creation (absolute loader)
     * @param projectName name of the project to load
     * @param routesFolder routes folder of parse (relative to project folder)
     * @param mainStage the main stage of the FXML states
     * @return The object Loader
     */
    public static Loader useNormalLoader(String projectName, String routesFolder, Stage mainStage)
    { return new Loader(projectName, routesFolder, mainStage); }

    /**
     * Hook for laxy loader creation (dynamic requests)
     * @param projectName name of the project to load
     * @param routesFolder routes folder of parse (relative to project folder)
     * @param mainStage the main stage of the FXML states
     * @return The object LazyLoader
     */
    public static LazyLoader useLazyLoader(String projectName, String routesFolder, Stage mainStage)
    { return new LazyLoader(projectName, routesFolder, mainStage); }

}
