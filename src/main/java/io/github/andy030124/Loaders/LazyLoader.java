package io.github.andy030124.Loaders;
import io.github.andy030124.Utils.FolderParser;
/* IMPORTS ESPECIFICS */
import javafx.stage.Stage;


public class LazyLoader extends MainLoader {

    /**
     * Constructor for initialize Object and load the first folders parse
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public LazyLoader(String projectName, String routesFolder, Stage mainStage){
        super(projectName, routesFolder, mainStage);
        load();
    }


    /* INTERFACE METHODS */

    /**
     * Load & launch only the main page
     */
    @Override
    public void load() {
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
            String pkgName = pkg.concat(
                "." + file.replaceAll("/", ".")
            );

            if(
                (
                    !file.toLowerCase().equals("index") 
                &&  !file.toLowerCase().equals("_index")
                &&  !file.toLowerCase().equals("_index_")
                )
                ||  file.contains("/")
            ){ continue; }

            try {
                Class<?> cls = getClass().getClassLoader().loadClass(pkgName);
                file = "/";
                _routes.put(file, cls);
                System.out.println("Loaded " + pkgName + " route: " + file);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

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
        if( _activeRoute.get().equals(route) ) return ret;

        Class<?> cls = _routes.get(route);
        if( cls == null )
            cls = find(route);
        ret = load_impl(cls, route);
        return ret; 
    }
    
}
