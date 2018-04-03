package profiler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.*;
import java.io.*;


public class Controller {
    @FXML private Label waifuName;
    @FXML private ImageView imageView;
    private ImageQ waifuQueue;

    private final int PORT_NUM = 49000;
    private final int MAX_W = 5;
    private String hostName;

    private Socket socket;
    private BufferedReader fromServer;
    private PrintWriter toServer;

    public void setHost(String hostName) {
        this.hostName = hostName;
    }
    @FXML
    public void initialize() {
        try {
            waifuQueue = new ImageQ(MAX_W);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Waifu nextWaifu = waifuQueue.getNextWaifu();
        imageView.setImage(new Image(nextWaifu.getWaifuImageURL()));
        waifuName.setText(nextWaifu.getWaifuName());
    }
    public void yesClick(ActionEvent e) {
        rateWaifu(true);
        updateWaifu();
    }
    public void noClick(ActionEvent e) {
        rateWaifu(false);
        updateWaifu();
    }
    private void updateWaifu() {
        // updates the waifu queue
        if (0 != waifuQueue.size()) {
            Waifu nextWaifu = waifuQueue.getNextWaifu();
            imageView.setImage(new Image(nextWaifu.getWaifuImageURL()));
            waifuName.setText(nextWaifu.getWaifuName());
        }
        else {
            System.exit(0);
        }
    }
    private void rateWaifu(boolean isYes) {
        initConnect();
        //Print the rating to Server
        if (isYes) {
            toServer.println("RATE,"+waifuName.getText()+"=true");
        } else {
            toServer.println("RATE,"+waifuName.getText()+"=false");
        }
        closeConnect();
    }
    private void initConnect() {
        try {
            //Load the socket with the hostName and port number
            socket = new Socket(hostName, PORT_NUM);
            //Connect input and output With the socket using Print Writer and BufferedReader
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    private void closeConnect() {
        try {
            fromServer.close();
            toServer.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("ERROR: Cannot close!");
            e.printStackTrace();
        }
    }
}
