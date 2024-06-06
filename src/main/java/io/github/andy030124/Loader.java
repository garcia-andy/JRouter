package io.github.andy030124;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.*;

public class Loader implements LoaderInterface {

    @Getter
    private Map<String,Class<?>> _routes = 
    new HashMap<String,Class<?>>();

    @Getter
    private Stage _mainStage;

    @Getter
    private FXMLLoader _loader;

    @Getter @Setter
    private Scene _scene;

    private String _projectFolder = 
    Loader.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    public URL loadSrc(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    public static URL LoadSrc(String path)
    { return Loader.class.getClassLoader().getResource(path); }

    private static String endSanitizer(String path){
        return (path.endsWith("/")) 
        ? path 
        : (path + "/");
    }

    public Loader(String projectName, String routesFolder, Stage mainStage){
        _mainStage = mainStage;
        _loader = new FXMLLoader();
        
        load(projectName, routesFolder);
    }

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

    public void loadView(String view, Object ctrl) throws IOException{
        _loader.setController(ctrl);
        _loader.setLocation(loadSrc(view));
        update(new Scene(_loader.load()));
    }

    public void update(Scene scn){
        _scene = scn;
        _mainStage.setScene(_scene);
        _mainStage.show();
    }


}
