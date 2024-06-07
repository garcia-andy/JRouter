package io.github.andy030124.Loaders;
/* IMPORTS ESPECIFICS */
import javafx.stage.Stage;
import io.github.andy030124.Utils.FolderParser;

/**
 * Principal Interface Implementation for use JRouterFX
 * @author Andy Garcia
 * @version 0.1, 2024/5/6
*/
public class Loader extends MainLoader {
    
    /**
     * Constructor for initialize Object and load the first folders parse
     * @param projectName the project name for load first place
     * @param routesFolder the routes folder for load first place
     * @param mainStage the main stage of the FXML application
     */
    public Loader(String projectName, String routesFolder, Stage mainStage){
        super(projectName, routesFolder, mainStage);
        load();
    }
    
    /**
     * Load in specific project folder, and load routes controllers
     */
    @Override
    public void load(){
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
    @Override
    public Object launch(String route){
        Object ret = null;
        if( _activeRoute.get().equals(route) ) return ret;

        Class<?> cls = _routes.get(route);
        ret = load_impl(cls, route);
        
        return ret; 
    }

}
