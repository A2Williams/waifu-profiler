package profiler;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class WaifuThread extends Thread {
    private String csvFile;
    private Socket socket = null;
    private BufferedReader getRequest;
    private PrintWriter sendResponse;
    private ArrayList<String> wafiuline;
    private Random rand = new Random();
    private int randNum;

    public WaifuThread(Socket socket, String csvFile) {
        this.csvFile = csvFile;
        this.socket = socket;
        
        String line = "";
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                wafiuline.add(i, line);
                i = i + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
        randNum = rand.nextInt(wafiuline.size());
        sendResponse.println(wafiuline.get(randNum));
        wafiuline.remove(randNum);
    }
    private void handleRate(String recievedToken) {
        // A function that updates the waifu's rating in the csv file
        // parses waifu name and rating
        // updates rating of waifu in csv file
        //Parse the received info
        StringTokenizer ST=new StringTokenizer(recievedToken,"=");
        String wifeName=ST.nextToken();
        String wifeBooleanResult=ST.nextToken();
        //Find and edit
        File file = new File("waifu.csv");
        try {
            ObservableList<String> futureline= FXCollections.observableArrayList();;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                // use comma as separator
                String line = sc.nextLine();
                //for testing System.out.println(line+" ~ "+threadName);
                //Append to list
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                //URL TOKEN
                String currentToken=tokenizer.nextToken();
                futureline.add(currentToken);
                //NAME TOKEN
                currentToken=tokenizer.nextToken();
                futureline.add(currentToken);
                //CHECK NAME TOKEN
                if (currentToken.equals(wifeName)) {
                    //IF THEY CHOSE YES AND NAME TOKEN IS THE SAME
                    if (wifeBooleanResult.equals("false")) {
                        //For True title ADD 1
                        int integerToken = Integer.parseInt(tokenizer.nextToken());
                        integerToken = integerToken + 1;
                        currentToken = String.valueOf(integerToken);
                        futureline.add(currentToken);
                        //For total ADD 1
                        integerToken = Integer.parseInt(tokenizer.nextToken());
                        integerToken = integerToken + 1;
                        currentToken = String.valueOf(integerToken);
                        futureline.add(currentToken);
                    }
                    //IF THEY CHOSE FALSE AND NAME TOKEN IS THE SAME
                    else{
                        //SKIP OVER TRUE TITLE
                        currentToken = tokenizer.nextToken();
                        futureline.add(currentToken);

                        //For total ADD 1
                        int integerToken = Integer.parseInt(tokenizer.nextToken());
                        integerToken = integerToken + 1;
                        currentToken = String.valueOf(integerToken);
                        futureline.add(currentToken);
                    }
                }
                else{
                    //NOT THE DESIRED WORD SKIP TO NEXT LINE
                    //TRUE TOKEN
                    currentToken=tokenizer.nextToken();
                    futureline.add(currentToken);
                    //TOTAL TOKEN
                    currentToken=tokenizer.nextToken();
                    futureline.add(currentToken);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
