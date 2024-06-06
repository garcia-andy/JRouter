package example;
import io.github.andy030124.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Sistema");
        try {
            Loader ld = new Loader("cujae","routes",primaryStage);
            //ld.launch("home/HomePage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
