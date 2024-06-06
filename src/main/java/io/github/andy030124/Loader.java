package io.github.andy030124;
/* IMPORTS ESPECIFICS */
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/* IMPORTS ALL */
import java.lang.reflect.*;
import java.util.*;
import lombok.*;

/**
 * Principal Interface Implementation for use JRouterFX
 * @author Andy Garcia
 * @version 0.1, 2024/5/6
*/
public class Loader implements LoaderInterface {

    /** 
     * Map with all routes and classes resolveds
    */
    @Getter
    private Map<String,Class<?>> _routes = 
    new HashMap<String,Class<?>>();

    /**
     * The main stage of the FXML application
     */
    @Getter
    private Stage _mainStage;

    /**
     * The FXML loader for load files views (.fxml)
     */
    @Getter
    private FXMLLoader _loader;

    /**
     * The viewed scene
     */
    @Getter @Setter
    private Scene _scene;

    /**
     * The project folder with all classes compileds
     */
    private String _projectFolder = 
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
    private static String endSanitizer(String path){
        return (path.endsWith("/")) 
        ? path 
        : (path + "/");
    }

    /**
     * Constructor for initialize Object and load the first folders parse
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public Loader(String projectName, String routesFolder, Stage mainStage){
        _mainStage = mainStage;
        _loader = new FXMLLoader();
        
        load(projectName, routesFolder);
    }

    
    /**
     * Load in specific project folder, and load routes controllers
     * @param projectName for the main folder 
     * @param routesFolder folder where placed Routers controllers 
     */
    public void load(String projectName, String routesFolder){
        String pkg = projectName + "." + routesFolder.replaceAll("/",".");

        String strt = endSanitizer(_projectFolder);
        routesFolder = endSanitizer(routesFolder);
        String projectNameFolder = endSanitizer(projectName);
        
        for(String file: FolderParser.printDir(
            strt + projectNameFolder
            ,strt + projectNameFolder + routesFolder,".class"
        )){

            String pkgName = pkg.concat(
                "." + file.replaceAll("/", ".")
            );

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

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } // for

        // finding default page
        for(String Key: _routes.keySet()){
            if(
                ((
                    Key.toLowerCase().contains("index") 
                    || Key.toLowerCase().contains("_index")
                )
                && !Key.contains("/") )
                || (Key.hashCode() == "/".hashCode())
            ){
                    launch(Key);
                    break;
            }
        }

    } // method load

    /**
     * Use route to execute the constructor controller
     * @param route Route registred to launch
     * @return Object of instance Controller route (maybe null)
     */
    public Object launch(String route){
        Object ret = null;

        Class<?> cls = _routes.get(route);
        String errSms = "Error while launch route -> " + route + " [";
        try {

            Constructor<?> constr = cls.getConstructor(LoaderInterface.class);
            if( constr != null )
            ret = constr.newInstance(this);

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

    /**
     * Load a view with a controller and update the main scene
     * @param view the view name file (relative to resources folder)
     * @param ctrl the controller for set of that file
     * @exception IOException maybe throw an exception trying create/load the scene
     */
    public void loadView(String view, Object ctrl) throws IOException{
        _loader.setController(ctrl);
        _loader.setLocation(loadSrc(view));
        update(new Scene(_loader.load()));
    }

    /**
     * Update the main scene
     * Automatic call by loadView
     * @param scn scene to update the main scene and main stage
     */
    public void update(Scene scn){
        _scene = scn;
        _mainStage.setScene(_scene);
        _mainStage.show();
    }


}
