package profiler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setHost(getParameters().toString());
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();//Gets the default monitor for resizing purposes
        Scene scene = new Scene(root, gd.getDisplayMode().getHeight()*(0.8*0.6), gd.getDisplayMode().getHeight()*0.8); //Sets the application to be 80% of the screen's height and 60% its own height in width.
        stage.setTitle("Waifu Profiler");
        stage.setScene(scene);
        stage.setResizable(false);
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
