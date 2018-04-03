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
import java.net.*;
import java.io.*;


public class Controller {
    @FXML private Label waifuName;
    @FXML private ImageView imageView;

    private final int PORT_NUM = 49000;
    private String hostName;

    private Socket socket;
    private BufferedReader fromServer;
    private PrintWriter toServer;

    public void setHost(String hostName) {
        this.hostName = hostName;
    }
    @FXML
    public void initialize() {
    }
    public void yesClick(ActionEvent e) {
        //Load the socket with the hostName and port number
        try {
            socket = new Socket(hostName, PORT_NUM);
            //Connect input and output With the socket using Print Writer and BufferedReader
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream());
            //Print the rating to Server
            toServer.println("RATE,"+waifuName.toString()+"="+true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void noClick(ActionEvent e) {
         //Load the socket with the hostName and port number
        try {
            socket = new Socket(hostName, PORT_NUM);
            //Connect input and output With the socket using Print Writer and BufferedReader
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream());
            //Print the rating to Server
            toServer.println("RATE,"+waifuName.toString()+"="+false);
        }catch(IOException e1){
            e1.printStackTrace();
        }
    }
}
