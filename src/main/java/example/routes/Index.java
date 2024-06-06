package example.routes;
import io.github.andy030124.LoaderInterface;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.*;

public class Index {
    @Getter
    Scene _scene;
    @Getter
    LoaderInterface _main;

    @FXML
    TextField userName;
    @FXML
    PasswordField userPass;

    public Index(LoaderInterface m) throws IOException{
        _main = m;
        _main.loadView("initForm.fxml", this);
    }
}
