package java;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    public ObservableList<Waifu> waifuList = FXCollections.observableArrayList();
    public ObservableList<Waifu> tempList = FXCollections.observableArrayList();
    private Label waifuNameL;
    private Button noB;
    private Button yesB;
    private static String delimiter = ",";
    public static String fileName = "waifus.csv";
    public int index;
    public Random rand = new Random();
    public int  randNum;
    private double rate;


    @Override
    public void start(Stage stage) throws Exception{

///////////////////////////////////////////////LOADIN WAFIUS INTO LIST/////////////////////////////////////////////////
        String line = "";
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            System.out.println("Loading Waifus...");
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] wafiuline = line.split(delimiter);
                waifuList.add(i, new Waifu(wafiuline[0],wafiuline[1]));
                i = i + 1;
                //System.out.println("Country [code= " + wafiuline[0] + " , name=" + wafiuline[1] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

/////////////////////////////////////////////END OF WAIFU LIST LOADING/////////////////////////////////////////////////

        //was for testing
        //String lk = "https://safebooru.org//images/2333/f55ed2e44d79debf99085ecdb5245724c0618cfa.jpg";
        //URL lol = new URL("https://safebooru.org//images/1471/d69bcc87e4bed79d0ba2ca61f27865fee684e36a.jpg?1540198");
        //Image image1 = new Image(lk);




        randNum = rand.nextInt(waifuList.size()) + 0;
        Image image = waifuList.get(randNum).getWaifuImage();
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);


        this.waifuNameL = new Label(waifuList.get(randNum).getWaifuName());
        //this.waifuNameT = new TextField("NAME");

        this.noB = new Button("NAH");
        this.noB.setDefaultButton(true);
        this.noB.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {


                waifuList.get(randNum).setWaifuYESNO("NO");

                rate = 0.0;

                waifuList.get(randNum).setWaifuRating(rate);
                tempList.add(waifuList.get(randNum));
                waifuList.remove(randNum);

                if(waifuList.size() == 0){
                    for(int k = 0; k < tempList.size(); k++){
                        System.out.println(tempList.get(k).getWaifuName() + " - " + " RATE: " + tempList.get(k).getWaifuRating() + ". Is Waifu: "
                        + tempList.get(k).getWaifuYESNO());
                    }
                    System.exit(0);
                }

                randNum = rand.nextInt(waifuList.size()) + 0;
                imageView.setImage(waifuList.get(randNum).getWaifuImage());
                waifuNameL.setText(waifuList.get(randNum).getWaifuName());





            }
        });

        this.yesB = new Button("YEA");
        this.yesB.setDefaultButton(true);
        this.yesB.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                List<Double> choices = new ArrayList<>();
                choices.add(1.0);
                choices.add(2.0);
                choices.add(3.0);
                choices.add(4.0);
                choices.add(5.0);


                ChoiceDialog<Double> dialog = new ChoiceDialog<>(1.0, choices);
                dialog.setTitle("Rate you Waifu!");
                dialog.setHeaderText("Rate it from 1 to 5");
                dialog.setContentText("Choose your Rating:");

                // Traditional way to get the response value.
                Optional<Double> result = dialog.showAndWait();
                if (result.isPresent()){
                    rate = result.get();
                }



                waifuList.get(randNum).setWaifuYESNO("YES");


                waifuList.get(randNum).setWaifuRating(rate);
                tempList.add(waifuList.get(randNum));
                waifuList.remove(randNum);

                if(waifuList.size() == 0){
                    for(int k = 0; k < tempList.size(); k++){
                        System.out.println(tempList.get(k).getWaifuName() + " - " + " RATE: " + tempList.get(k).getWaifuRating() + ". Is Waifu: "
                                + tempList.get(k).getWaifuYESNO());
                    }
                    System.exit(0);
                }

                randNum = rand.nextInt(waifuList.size()) + 0;
                imageView.setImage(waifuList.get(randNum).getWaifuImage());
                waifuNameL.setText(waifuList.get(randNum).getWaifuName());

            }
        });


        //Create the form layout
        GridPane bottom = new GridPane();
        bottom.setPadding(new Insets(10));
        bottom.setHgap(10);
        bottom.setVgap(10);

        bottom.add(yesB, 1, 1);
        bottom.add(noB, 2, 1);
        bottom.add(waifuNameL,20,1);

        StackPane root = new StackPane();


        root.getChildren().addAll(imageView,bottom);
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws Exception{




        launch(args);
    }
}
