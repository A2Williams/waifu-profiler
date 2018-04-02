package profiler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class WaifuThread implements Runnable{
    //Declare Variables for class
    private Thread thread;
    private String threadName;
    private String filename;
    //Used to find the position in which the thread will grab info from to prevent
    //Duplicate photos
    private int position;
    private int limit;
    //the Master List
    public ObservableList<Waifu> waifuList = FXCollections.observableArrayList();
    //Initialize the thread with the required info
    WaifuThread(String name,String fileName,int pos,int upperlim) {
        limit=upperlim;
        threadName = name;
        filename = fileName;
        position =pos;
        //System.out.println("Creating " + threadName);
    }
    //Check if thread it is still active for if statements to make sure the function calling it wont go past till its done
    public boolean isAlive(){
        return thread.isAlive();
    }
    //Run Thread
    public void run(){
        //System.out.println("RUN-"+threadName);
        //Set variables
        String delimiter = ",";
        String row = "";
        int currentIterator = 0;
        File file=new File(filename);
        Scanner scanner = null;
        //Initilize Scanner
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Prep for the loop
        int count=0;
        //If this isnt the first thread position wont equal 0
        //Then set it so nextLine will proper position line
        if(position !=0) {
            while (position > count) {
                scanner.nextLine();
                count=count+1;
            }
        }
        //Start Algorithm to load the data from the csv
        System.out.println("Loading Waifus... Thread:"+threadName);
        //Prevent the scanner from scanning nothing AND not going over the limit of how much to load in this
            while (scanner.hasNextLine() && currentIterator<limit) {
                // use comma as separator
                row=scanner.nextLine();
                //Split the line by the delimiter
                String[] wafiuline = row.split(delimiter);
                //for testing System.out.println(line+" ~ "+threadName);
                //Append to list
                waifuList.add(currentIterator, new Waifu(wafiuline[0],wafiuline[1]));
                //iterate the iterator of the loop
                currentIterator=currentIterator+1;
                //System.out.println("Country [code= " + wafiuline[0] + " , name=" + wafiuline[1] + "]");
            }
       // System.out.println("DONE");
    }
    public ObservableList<Waifu> start(){
        //System.out.println("Starting " +  threadName );

        //Initialize and run thread then return the master list
        thread =new Thread(this,threadName);
        thread.start();//run();
        return waifuList;
    }

    }



