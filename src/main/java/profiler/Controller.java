package profiler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.io.*;
import java.nio.Buffer;
import java.util.Random;
import java.util.Scanner;

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
        ObservableList<Waifu> t1 = FXCollections.observableArrayList();
        ObservableList<Waifu> t2 = FXCollections.observableArrayList();
        File file=new File("waifus.csv");
        Scanner sc= null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int waifuCount=0;
        while(sc.hasNextLine()){
            sc.nextLine();
            waifuCount=waifuCount+1;
        }
        //Essentially what im going to do is have multiple threads run to divide up the work of going through the function
        //Problems are:
        //Need to figure out how many waifus is automatically to know how many times/threads
        //need to append to the waifulist each time
        int iterator=0;
        int count=0;
        int backup=waifuCount;
        if(waifuCount % 2 != 0){
            backup=waifuCount+1;
        }
            WaifuThread wt = new WaifuThread("T1", fileName, iterator,backup/2);

            iterator=iterator+waifuCount/2;

            WaifuThread wt2 = new WaifuThread("T2", fileName, iterator,waifuCount/2);

            iterator=iterator+5;
            t1 = wt.start();
            t2 = wt2.start();
            try {
                while(wt.isAlive()) {
                    Thread.sleep(50);
                }
                while(wt2.isAlive()) {
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("8"+"\nT1.size:"+t1.size());
            for(int i=0;i<t1.size();i++){
                waifuList.add(count,t1.get(i));
                count=count+1;
            }
            System.out.println("8"+"\nT2.size:"+t2.size());
            for(int p=0;p<t2.size();p++){
                waifuList.add(count,t2.get(p));
                count=count+1;
            }
            t1.removeAll();
            t2.removeAll();
    }
        /*
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
    }
    */
}
