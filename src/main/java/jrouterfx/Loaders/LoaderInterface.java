package jrouterfx.Loaders;

import java.io.IOException;

/**
 * Loader Interface for implements 
 * @author Andy Garcia
 * @version 0.1, 2024/5/6
*/
public interface LoaderInterface  {
    /**
     * Load in specific project folder, and load routes controllers
     */
    public void load();
    /**
     * Use route to execute the constructor controller
     * @param route Route registred to launch
     * @return Object of instance Controller route (maybe null)
     */
    public Object launch(String route);
    /**
     * Load a view with a controller and update the main scene
     * @param view the view name file (relative to resources folder)
     * @param ctrl the controller for set of that file
     * @exception IOException maybe throw an exception trying create/load the scene
     */
    public void loadView(String view, Object ctrl) throws IOException;
}
