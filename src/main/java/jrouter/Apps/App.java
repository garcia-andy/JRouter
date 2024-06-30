package jrouter.Apps;

import jrouter.GuiProviders.GuiProvider;
import jrouter.Loaders.MainLoader;
import jrouter.reactive.Signal;
import lombok.Getter;

/**
 * The Abstraction for automatic loaders gesture
 */
public class App<SceneT> {
    private ApplicationProvider<SceneT> _provider;
    private GuiProvider<SceneT> _guiProvider;
    private Signal<String> _title = new Signal<String>("Default Title");
    
    @Getter
    protected MainLoader<SceneT> _ld;

    /**
     * Constructor with default Provider (Absolute Loader)
     * @param title the title of the window
     */
    public App(String title)
    { this(title, new DefaultProvider<SceneT>()); }

    /**
     * Constructor with Specific Provider (Absolute or Lazy Loader)
     * @param title the title of the window
     * @param provider the provider for Lazy or Absolute Loader construction
     */
    public App(String title, ApplicationProvider<SceneT> provider){ 
        _provider = provider;
        _title = new Signal<String>(title);
        _title.onChange((t) -> {
            if( _guiProvider != null ) _guiProvider.setTitle(t.get());
        });
    }

    /**
     * Set the GUI Provider
     * @param prov the new provider
     */
    public void setGuiProvider(GuiProvider<SceneT> prov){
        _guiProvider = prov;
    }

    /**
     * Get the GUI Provider
     */
    public GuiProvider<SceneT> getGuiProvider(){
        return _guiProvider;
    }

    /**
     * Start the Application with an already setted gui provider
     */
    public void start(){ start(null); }

    /**
     * Start the Application
     * @param prov the Abstraction Layer Handler GUI (if is null, only return)
     */
    public void start(GuiProvider<SceneT> prov){
        _guiProvider = prov == null ? _guiProvider : prov;
        if( _guiProvider == null ) return;

        try {
            _guiProvider.setTitle(_title.get());
            _ld = _provider.provide(_guiProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            _guiProvider.show();
        }
    }

    public String getTitle(){ return _title.get(); }
    public void setTitle(String newTitle){ _title.set(newTitle); }

}
