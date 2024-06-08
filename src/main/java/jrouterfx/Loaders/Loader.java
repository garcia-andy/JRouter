package jrouterfx.Loaders;
/* IMPORTS ESPECIFICS */
import javafx.stage.Stage;
import jrouterfx.hooks.JRouter;

/**
 * Principal Interface Implementation for use JRouterFX
 * @author Andy Garcia
 * @version 0.1, 2024/5/6
*/
public class Loader extends MainLoader {
    
    /**
     * Constructor for initialize Object and load the first folders parse
     * @param c Class for load working directory
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public Loader(Class<?> c, String projectName, String routesFolder, Stage mainStage){
        super(c,projectName, routesFolder, mainStage);
        load();
    }

    /**
     * Constructor for initialize Object and load the first folders parse
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public Loader(String projectName, String routesFolder, Stage mainStage)
    { this(null,projectName, routesFolder, mainStage); }
    
    /**
     * Loading file partitions 
     * @param file file route
     * @param cls class to save
     * @param pkgName pkg name
     */
    public void loadParts(String file, Class<?> cls, String pkgName){
        String[] parts = file.split("/");
        if( parts.length <= 0 ) return;

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

    /**
     * Load in specific project folder, and load routes controllers
     */
    @Override
    public void load(){
        loader( (String file, String pkgName, Class<?> cls, JRouter router) -> {
            if(
            (
                file.toLowerCase().contains("index") 
                || file.toLowerCase().contains("_index")
            )
            && !file.contains("/") ){
                file = "/";
            }else if( router.url().equals("") && router.value().equals("") ){
                loadParts(file,cls,pkgName);
            }
            _routes.put(file, cls);
            System.out.println("Loaded " + pkgName + " route: " + file);
            return true;
        } );

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
    @Override
    public Object launch(String route){
        Object ret = null;
        if( 
                _activeRoute.get() != null 
            &&  _activeRoute.get().equals(route) 
        ) return ret;

        Class<?> cls = _routes.get(route);
        ret = load_impl(cls, route);
        
        return ret; 
    }

}
