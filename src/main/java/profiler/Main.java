package profiler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;


public class Main extends Application {
    protected ClientConnect client;
    @Override
    public void start(Stage stage) throws Exception{
        client = new ClientConnect(this.getParameters().toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Waifu Profiler");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }


    public static void main(String[] args) {
        if (1 == args.length) {
            launch(args);
        } else {
            System.err.println("ERROR: few arguments usage: " +
                    "javac Main.java <hostname>");
            System.exit(-1);
        }
    }
}
