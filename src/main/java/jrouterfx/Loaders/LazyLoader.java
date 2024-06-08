package jrouterfx.Loaders;
import jrouterfx.hooks.JRouter;
/* IMPORTS ESPECIFICS */
import javafx.stage.Stage;


public class LazyLoader extends MainLoader {

    /**
     * Constructor for initialize Object and load the first folders parse
     * @param c Class for load working directory
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public LazyLoader(Class<?> c, String projectName, String routesFolder, Stage mainStage){
        super(c,projectName, routesFolder, mainStage);
        load();
    }

    /**
     * Constructor for initialize Object and load the first folders parse
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public LazyLoader(String projectName, String routesFolder, Stage mainStage){
        this(null, projectName, routesFolder, mainStage);
    }


    /* INTERFACE METHODS */

    /**
     * Load & launch only the main page
     */
    @Override
    public void load() {
        loader((String file, String pkgName, Class<?> cls,JRouter router) -> {
            if(
                (router.url().equals("") && router.value().equals(""))
                && 
                (
                    (
                        !file.toLowerCase().equals("index") 
                    &&  !file.toLowerCase().equals("_index")
                    &&  !file.toLowerCase().equals("_index_")
                    )
                    ||  file.contains("/")
                )
            ){ return true; }

            file = "/";
            _routes.put(file, cls);
            System.out.println("Loaded " + pkgName + " route: " + file);
            return false;
        });

        if( _routes.containsKey("/") ) launch("/");
    }

    /**
     * Use route to execute the constructor controller
     * @param route Route registred to launch
     * @return Object of instance Controller route (maybe null)
     */
    @Override
    public Object launch(String route) {
        Object ret = null;
        if( 
            _activeRoute.get() != null 
            &&  _activeRoute.get().equals(route)
        ) return ret;

        Class<?> cls = _routes.get(route);
        if( cls == null )
            cls = find(route);
        ret = load_impl(cls, route);
        return ret; 
    }
    
}
