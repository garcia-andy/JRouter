package jrouter.GuiProviders;
import java.net.URL;

public abstract class GuiProvider<SceneT> implements InterfaceGuiProvider<SceneT> {

    /**
     * Utility for load a resource file in the resources folder
     * @param path String with the relative resource path
     * @return URL with the absolute path resource
    */
    public URL loadSrc(String path)
    { return getClass().getClassLoader().getResource(path); }

    @Override
    public void update(SceneT scn) {
        setScene(scn);
        show();
    }

    
}
