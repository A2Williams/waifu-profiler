package profiler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WaifuServer {
    private final int PORT_NUM = 49000;
    private ServerSocket serverSocket = null;
    private String csvFile;

    private WaifuServer(String csvFile) {
        this.csvFile = csvFile;
        try {
            serverSocket = new ServerSocket(PORT_NUM);
            System.out.println("Server initialized on port" + PORT_NUM + "^w^!");
        } catch (IOException e) {
            System.err.println("ERROR: IOException while initializing server");
            e.printStackTrace();
        }
    }
    private void requestListener() throws IOException {
        // accepts client connections and handles requests
        while(true) {
            Socket clientSocket = serverSocket.accept();
            WaifuThread handler = new WaifuThread(clientSocket,csvFile);
            handler.start();
        }

    }
    public static void main(String[] args) {
        WaifuServer server;
        // start server with a .csv file of image urls
        if(0 == args.length) {
            System.out.println("WARNING: No .csv file specified." +
                    " using waifus.csv");
            server = new WaifuServer("waifus.csv");
        }
        else {
            server = new WaifuServer(args[0]);
        }
        try {
            server.requestListener();
        } catch (IOException e) {
            System.err.println("ERROR: " +
                    "IOException while connecting to client");
            e.printStackTrace();
        }
    }
}
