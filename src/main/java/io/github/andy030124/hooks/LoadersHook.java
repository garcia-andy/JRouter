package io.github.andy030124.hooks;

import io.github.andy030124.Loaders.*;
import javafx.stage.Stage;

public class LoadersHook {

    public static Loader useNormalLoader(String projectName, String routesFolder, Stage mainStage)
    { return new Loader(projectName, routesFolder, mainStage); }

    public static LazyLoader useLazyLoader(String projectName, String routesFolder, Stage mainStage)
    { return new LazyLoader(projectName, routesFolder, mainStage); }

}
