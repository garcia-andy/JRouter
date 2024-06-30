package jrouter.GuiProviders;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import lombok.*;

/**
 * A GUI Provider for JavaFX
 */
public class JFXProvider extends GuiProvider<Scene> {

    /**
     * The main stage of the FXML application
     */
    @Getter
    protected Stage _mainStage;

    /**
     * The FXML loader for load files views (.fxml)
     */
    @Getter
    protected FXMLLoader _loader = new FXMLLoader();

    /**
     * The viewed scene
     */
    @Getter @Setter
    protected Scene _scene;

    /**
     * The JavaFX Provider need the JavaFX Main Stage
     * !(view documentation of JavaFX for more info)
     * @param mainStage the main stage of JavaFX Application
     */
    public JFXProvider(Stage mainStage){
        _mainStage = mainStage;
        _scene = _mainStage.getScene();
    }

    /**
     * Setting a scene in the internal variable, and in the main stage
     * @param scn the new scene to set
     */
    @Override
    public void setScene(Scene scn){
        _scene = scn;
        _mainStage.setScene(_scene);
    }

    /**
     * Load a view using the fxml loader 
     * ! if the view String, is an view invalid, infinite loop on while
     * ? (not be used externally, only for MainLoader)
     * @param view The view for load
     * @param ctrl The object to set as Controller
     */
    @Override
    public void loadView(String view, Object ctrl) throws IOException {
        _loader = new FXMLLoader();
        _loader.setController(ctrl);
        //! TODO Location maybe set no resources found
            URL file = loadSrc(view);
            while( file == null ) file = loadSrc(view);
            _loader.setLocation(file);
        update(new Scene(_loader.load()));
    }

    /**
     * Show the changes in the main stage
     */
    @Override
    public void show() 
    { _mainStage.show(); }

    /**
     * Setting the title of the screen
     * @param title the new title
     */
    @Override
    public void setTitle(String title) {
        _mainStage.setTitle(title);
    }

    /**
     * Getting the Title of the screen
     */
    @Override
    public String getTitle()
    { return _mainStage.getTitle(); }
}
