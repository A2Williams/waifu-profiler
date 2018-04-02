package profiler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Controller {
    @FXML private Label waifuName;
    @FXML private ImageView imageView;
    @FXML private Slider rating;

    public ObservableList<Waifu> waifuList = FXCollections.observableArrayList();
    public ObservableList<Waifu> tempList = FXCollections.observableArrayList();

    private Random rand = new Random();
    private int randNum;
    @FXML
    public void initialize() {
        // initializing list of waifus
        loadWaifusCSV("waifus.csv");
        // load the first waifu
        getRandomWaifu();
    }
    public void yesClick(ActionEvent e) {
        waifuList.get(randNum).setWaifuYESNO("YES");
        double rate = rating.getValue();
        waifuList.get(randNum).setWaifuRating(rate);
        tempList.add(waifuList.get(randNum));
        waifuList.remove(randNum);
        if(waifuList.size() == 0){
            for(int k = 0; k < tempList.size(); k++){
                ReadWriter rw=new ReadWriter();
                rw.saveWaifu(tempList.get(k).getWaifuRating(),tempList.get(k).getWaifuName());
                System.out.println(
                        tempList.get(k).getWaifuName() + " - " + " RATE: " + tempList.get(k).getWaifuRating() + ". Is Waifu: "
                                + tempList.get(k).getWaifuYESNO());
            }
            System.exit(0);
        }
        getRandomWaifu();
    }
    public void noClick(ActionEvent e) {
        waifuList.get(randNum).setWaifuYESNO("NO");
        double rate = 0.0;
        waifuList.get(randNum).setWaifuRating(rate);
        tempList.add(waifuList.get(randNum));
        waifuList.remove(randNum);
        if(waifuList.size() == 0){
            for(int k = 0; k < tempList.size(); k++){
                ReadWriter rw=new ReadWriter();
                rw.saveWaifu(tempList.get(k).getWaifuRating(),tempList.get(k).getWaifuName());
                System.out.println(
                        tempList.get(k).getWaifuName() + " - " + " RATE: " + tempList.get(k).getWaifuRating() + ". Is Waifu: "
                        + tempList.get(k).getWaifuYESNO());
            }
            System.exit(0);
        }
        getRandomWaifu();
    }
    private void getRandomWaifu() {
        randNum = rand.nextInt(waifuList.size());
        imageView.setImage(waifuList.get(randNum).getWaifuImage());
        waifuName.setText(waifuList.get(randNum).getWaifuName());
    }
    private void loadWaifusCSV(String fileName) {
        String delimiter = ",";
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
        System.out.println(waifuList);
    }

}
