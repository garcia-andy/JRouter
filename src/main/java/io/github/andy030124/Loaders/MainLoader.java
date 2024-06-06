package io.github.andy030124.Loaders;
/* IMPORTS ESPECIFICS */
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/* IMPORTS ALL */
import java.util.*;

import io.github.andy030124.Utils.FolderParser;
import io.github.andy030124.hooks.*;
import io.github.andy030124.reactive.*;
import lombok.*;

/**
 * Principal Abstract Interface Implementation for use JRouterFX
 * @author Andy Garcia
 * @version 0.1, 2024/5/6
*/
public abstract class MainLoader implements LoaderInterface {
    @Getter
    protected Signal<String> _activeRoute = SignalHook.useSignal();
    @Getter
    protected State<Object> _activeController = StatesHook.useState();

    /** 
     * Map with all routes and classes resolveds
    */
    @Getter
    protected Map<String,Class<?>> _routes = 
    new HashMap<String,Class<?>>();

    /**
     * The main stage of the FXML application
     */
    @Getter
    protected Stage _mainStage;

    /**
     * The FXML loader for load files views (.fxml)
     */
    @Getter
    protected FXMLLoader _loader;

    /**
     * The viewed scene
     */
    @Getter @Setter
    protected Scene _scene;

    /**
     * The Project Name
     */
    @Getter @Setter
    protected String _projectName;

    /**
     * The Routes Name
     */
    @Getter @Setter
    protected String _routesName;

    /**
     * The project folder with all classes compileds
     */
    protected String _projectFolder = 
    Loader.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    /**
     * Utility for load a resource file in the resources folder
     * @param path String with the relative resource path
     * @return URL with the absolute path resource
    */
    public URL loadSrc(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    /**
     * Utility for load a resource file in the resources folder from an static 
     * @param path String with the relative resource path
     * @return URL with the absolute path resource
     */
    public static URL LoadSrc(String path)
    { return Loader.class.getClassLoader().getResource(path); }

    /**
     * Sanitize an url with add the final '/' if not exists
     * @param path
     * @return String with the sanitized path
     */
    protected static String endSanitizer(String path){
        return (path.endsWith("/")) 
        ? path 
        : (path + "/");
    }

    /**
     * Constructor for initialize
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public MainLoader(String projectName, String routesFolder, Stage mainStage){
        _projectName = projectName;
        _routesName = routesFolder;
        _mainStage = mainStage;
        _loader = new FXMLLoader();
    }


    /**
     * Load all finding a route
     * @param route The route to search
     * @return Class<?> loaded from that route
    */
    protected Class<?> find(String route){
        Class<?> ret = null;

        String pkg = _projectName + "." + _routesName.replaceAll("/",".");

        String strt = endSanitizer(_projectFolder);
        _routesName = endSanitizer(_routesName);
        String projectNameFolder = endSanitizer(_projectName);

        for(String file: FolderParser.printDir(
            strt + projectNameFolder
            ,strt + projectNameFolder + _routesName,".class"
        )){

            String pkgName = pkg.concat(
                "." + file.replaceAll("/", ".")
            );

            if( !file.contains(route) ) continue;

            try {

                Class<?> cls = getClass().getClassLoader().loadClass(pkgName);
                if(
                (
                    file.toLowerCase().contains("index") 
                    || file.toLowerCase().contains("_index")
                )
                && !file.contains("/") ){
                    file = "/";
                }else{
                    String[] parts = file.split("/");
                    if( 
                        file.contains("/") 
                        && (parts[parts.length-1])
                        .toLowerCase().contains("index")
                    ){

                        String reconstruct = parts[0];
                        for(int i=1; i < (parts.length-1); i++)
                            reconstruct = endSanitizer(reconstruct) + parts[i];

                        _routes.put(reconstruct, cls);
                        System.out.println(
                            "Loaded " 
                            + pkgName + 
                            " route: " 
                            + reconstruct
                        );                
                    }
                }

                _routes.put(file, cls);
                System.out.println("Loaded " + pkgName + " route: " + file);
                ret = cls;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            break;
        }

        return ret;
    }

    protected Object load_impl(Class<?> cls, String route){
        Object ret = null;
        if( cls == null ) return ret;

        String errSms = "Error while launch route -> " + route + " [";
        try {

            Constructor<?> constr = cls.getConstructor(MainLoader.class);
            if( constr != null ){
                ret = constr.newInstance(this);
                _activeRoute.set(route);
                _activeController.set(ret);
            }

        } catch (
                NoSuchMethodException 
            |   SecurityException 
            |   InstantiationException 
            |   IllegalAccessException
            |   IllegalArgumentException 
            |   InvocationTargetException 
        e){ e.printStackTrace(); errSms += e.getMessage() + "]"; }

        System.out.println(
            (ret != null) 
            ? ("Launching Route -> " + route)
            : (errSms)
        );

        return ret;
    }


    /* Interface Methods */

    /**
     * Abstract method for load in specific project folder, 
     * and load routes controllers
     */
    @Override
    public abstract void load();

    /**
     * Abstract method for use route to execute the constructor controller
     * @param route Route registred to launch
     * @return Object of instance Controller route (maybe null)
     */
    @Override
    public abstract Object launch(String route);


    /**
     * Load a view with a controller and update the main scene
     * @param view the view name file (relative to resources folder)
     * @param ctrl the controller for set of that file
     * @exception IOException maybe throw an exception trying create/load the scene
     */
    @Override
    public void loadView(String view, Object ctrl) throws IOException {
        _loader.setController(ctrl);
        _loader.setLocation(loadSrc(view));
        update(new Scene(_loader.load()));
    }

    /**
     * Update the main scene
     * Automatic call by loadView
     * @param scn scene to update the main scene and main stage
     */
    @Override
    public void update(Scene scn) {
        _scene = scn;
        _mainStage.setScene(_scene);
        _mainStage.show();
    }
    
}
