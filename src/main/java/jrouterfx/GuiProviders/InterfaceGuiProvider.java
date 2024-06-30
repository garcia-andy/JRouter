package jrouterfx.GuiProviders;
import java.io.IOException;

public interface InterfaceGuiProvider<Scene_t> {
    /**
     * Update with a new Scene
     * @param scn Scene to replace the older Scene
     */
    void update(Scene_t scn);
    /**
     * Set a new Scene
     * @param scn Scene to replace the older Scene
     */
    void setScene(Scene_t scn);

    /**
     * Load a view from named view 
     * @param view The view for load (Potentially be an external file)
     * @param ctrl The object to Controll this layout
     * @throws IOException Possibly can't open the File
     */
    void loadView(String view, Object ctrl) throws IOException;
    /**
     * Show all changes in the screen
     */
    void show();
    
    /**
     * Setting the Title of the screen
     * @param title the new title to set in the native screen
     */
    void setTitle(String title);

    /**
     * Getting the Title of the screen
     */
    String getTitle();
}
