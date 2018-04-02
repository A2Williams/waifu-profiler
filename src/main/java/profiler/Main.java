package profiler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 800);
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) { launch(args); }
}
