package profiler;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.*;
import java.util.*;


public class Main extends Application {

    public int index;
    public Random rand = new Random();
    public int  randNum;
    private double rate;


    @Override
    public void start(Stage stage) throws Exception{

///////////////////////////////////////////////LOADIN WAFIUS INTO LIST/////////////////////////////////////////////////


/////////////////////////////////////////////END OF WAIFU LIST LOADING/////////////////////////////////////////////////

        //was for testing
        //String lk = "https://safebooru.org//images/2333/f55ed2e44d79debf99085ecdb5245724c0618cfa.jpg";
        //URL lol = new URL("https://safebooru.org//images/1471/d69bcc87e4bed79d0ba2ca61f27865fee684e36a.jpg?1540198");
        //Image image1 = new Image(lk);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 800);
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) { launch(args); }
}
