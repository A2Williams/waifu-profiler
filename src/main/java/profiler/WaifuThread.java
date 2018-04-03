package profiler;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class WaifuThread extends Thread {
    private String csvFile;
    private Socket socket = null;
    private BufferedReader getRequest;
    private PrintWriter sendResponse;

    public WaifuThread(Socket socket, String csvFile) {
        this.csvFile = csvFile;
        this.socket = socket;
        // initializing read/write stream connections
        try {
            getRequest = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()));
            sendResponse = new PrintWriter(this.socket.getOutputStream(),true);
        } catch (IOException e) {
            System.err.println("ERROR: " +
                    "IOException while initializing read/write connection");
            e.printStackTrace();
        }
    }
    public void run() {
        String request;
        // read a GET or RATE request from client
        try {
            request = getRequest.readLine();
            handleRequest(request);
        } catch (IOException e) {
            System.err.println("ERROR: No request sent...");
            e.printStackTrace();
        } finally {
            // close all streams and socket
            try {
                getRequest.close();
                sendResponse.close();
                socket.close();
            } catch (IOException e) {
                System.err.println("ERROR: Cannot close!");
                e.printStackTrace();
            }
        }
    }
    private void handleRequest(String req) {
        StringTokenizer tokenizer = new StringTokenizer(req, ",");
        // check if first token is a GET; if not a GET it's a RATE
        Boolean isGet = tokenizer.nextToken().equals("GET");
        if (isGet) {
            handleGet();
        } else {
            handleRate(tokenizer.nextToken());
        }
    }
    private void handleGet() {
        // A function that sends the client a random waifu from the csv file
        // TODO: send random waifu to client
    }
    private void handleRate(String ratedWaifu) {
        // A function that updates the waifu's rating in the csv file
        // TODO: parse waifu name and rating
        // TODO: update rating of waifu in csv file
    }
}
