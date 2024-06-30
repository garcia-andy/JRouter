package jrouterfx.Loaders;
/* IMPORTS ESPECIFICS */
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
/* IMPORTS ALL */
import java.util.*;

import jrouterfx.Api.Route;
import jrouterfx.GuiProviders.InterfaceGuiProvider;
import jrouterfx.Utils.FolderParser;
import jrouterfx.hooks.*;
import jrouterfx.reactive.*;
import lombok.*;

/**
 * Principal Abstract Interface Implementation for use JRouterFX
 * @author Andy Garcia
 * @version 0.1, 2024/5/6
*/
public abstract class MainLoader<SceneType> implements LoaderInterface {
    @Getter
    protected Signal<String> _activeRoute = SignalHook.useSignal();
    @Getter
    protected State<Object> _activeController = StatesHook.useState();

    Class<?> _class;

    /** 
     * Map with all routes and classes resolveds
    */
    @Getter
    protected Map<String,Class<?>> _routes = 
    new HashMap<String,Class<?>>();

    /**
     * The GUI Provider
     */
    InterfaceGuiProvider<SceneType> _provider;

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
        return ((_class == null) ? getClass() : _class)
        .getClassLoader().getResource(path);
    }

    /**
     * Getting the path of the folder from a class
     * @param c class for get directory work
     * @return String Directory Path
     */
    public static String getClassesWork(Class<?> c)
    { return c.getProtectionDomain().getCodeSource().getLocation().getFile(); }

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
     * Get the project name in the System Property exec.mainClass
     * @return the name of the main Project
     */
    public static String getProjectNameDetected(){
        String mainApp = System.getProperty("exec.mainClass");
        int idx = (mainApp.indexOf(".") <= 0) 
            ? mainApp.length() 
            : mainApp.indexOf(".")
        ;
        return mainApp.substring(0, idx);
    }


    /**
     * Get the project MainClass in the System Property exec.mainClass
     * @return the Class of the main Project
     */
    public static Class<?> getMainClass(){
        String mainApp = System.getProperty("exec.mainClass");
        try {
            return MainLoader.class.getClassLoader().loadClass(mainApp);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Constructor for initialize
     * @param classType class for load working directory
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param prov The GUI Provider
     */
    public MainLoader(
        Class<?> classType,
        String projectName, 
        String routesFolder,
        InterfaceGuiProvider<SceneType> prov
    ){
        _class = classType;
        _projectFolder = getClassesWork(classType!=null ? classType : getClass());
        _projectName = projectName;
        _routesName = routesFolder;
        _provider = prov;
    }

    /**
     * Constructor for initialize
     * @param prov The GUI Provider
     */
    public MainLoader(InterfaceGuiProvider<SceneType> prov){
        _class = getMainClass();
        _projectFolder = getClassesWork(_class!=null ? _class : getClass());
        _projectName = getProjectNameDetected();
        _routesName = "routes";
        _provider = prov;
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
                if( cls == null ) continue;

                if(
                (
                        file.toLowerCase().contains("index") 
                    ||  file.toLowerCase().contains("_index")
                )   && !file.contains("/") 
                ){
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
        Route router = cls.getAnnotation(Route.class);

        String errSms = "Error while launch route -> " + route + " [";
        try {
            Constructor<?> constr = cls.getConstructor(MainLoader.class);
            ret = constr.newInstance(this);
            _activeRoute.set(route);
            _activeController.set(ret);

            if( !router.view().trim().equals("") )
                loadView(router.view(), ret);

        } catch (
                NoSuchMethodException 
            |   SecurityException 
            |   InstantiationException 
            |   IllegalAccessException
            |   IllegalArgumentException 
            |   InvocationTargetException 
            |   IOException
        e){ e.printStackTrace(); errSms += e.getMessage() + "]"; }
        finally{
            System.out.println(
                (ret != null) 
                ? ("Launching Route -> " + route)
                : (errSms)
            );
        }

        return ret;
    }

    protected interface LoaderCallable{
        boolean run(String file, String pkg, Class<?> cls,Route router);
    }

    protected void loader(LoaderCallable c){
        String projectName = _projectName; 
        String routesFolder = _routesName;

        String pkg = projectName + "." + routesFolder.replaceAll("/",".");

        String strt = endSanitizer(_projectFolder);
        routesFolder = endSanitizer(routesFolder);
        String projectNameFolder = endSanitizer(projectName);

        for(String file: FolderParser.printDir(
            strt + projectNameFolder
            ,strt + projectNameFolder + routesFolder,".class"
        )){
            String pkgName = pkg
                .concat(
                    "." 
                    + file.replaceAll("/", ".")
                );
            try {
                Class<?> cls = 
                getClass().getClassLoader().loadClass(pkgName);
                
                Route router = cls.getAnnotation(Route.class);
                if( router == null ) continue;
                
                if( !router.url().equals("") )
                    file = router.url();
                else if( !router.value().equals("") )
                    file = router.value();

                if( 
                    !c.run(file,pkgName,cls,router)
                ) break;
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
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
        _provider.loadView(view, ctrl);
    }
}
