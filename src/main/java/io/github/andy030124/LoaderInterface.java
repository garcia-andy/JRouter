package io.github.andy030124;

import java.io.IOException;
import javafx.scene.Scene;

public interface LoaderInterface {
    public void load(String projectName, String routesFolder);
    public Object launch(String route);
    public void loadView(String view, Object ctrl) throws IOException;
    public void update(Scene scn);
}
