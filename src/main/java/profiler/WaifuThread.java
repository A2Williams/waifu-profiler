package profiler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class WaifuThread implements Runnable{
    private Thread t;
    private String threadName;
    private String fname;
    private int i;
    private int limit;
    public ObservableList<Waifu> waifuList = FXCollections.observableArrayList();

    WaifuThread(String name,String fileName,int c,int upperlim) {
        limit=upperlim;
        threadName = name;
        fname = fileName;
        i=c;
        //System.out.println("Creating " + threadName);
    }
    public boolean isAlive(){
        return t.isAlive();
    }
    public void run(){
        //System.out.println("RUN-"+threadName);
        String delimiter = ",";
        String line = "";
        int currentIterator = 0;
        File file=new File(fname);
        Scanner br = null;
        try {
            br = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int count=0;
        if(i!=0) {
            while (i > count) {
                br.nextLine();
                count=count+1;
            }
        }
        System.out.println("Loading Waifus... Thread:"+threadName);
            while (br.hasNextLine() && currentIterator<limit) {
                // use comma as separator
                String kine=br.nextLine();
                String[] wafiuline = kine.split(delimiter);
                //for testing System.out.println(kine+" ~ "+threadName);
                waifuList.add(currentIterator, new Waifu(wafiuline[0],wafiuline[1]));
                currentIterator=currentIterator+1;
                i = i + 1;
                //System.out.println("Country [code= " + wafiuline[0] + " , name=" + wafiuline[1] + "]");
            }

       // System.out.println("DONE");

    }
    public ObservableList<Waifu> start(){
        //System.out.println("Starting " +  threadName );
        t=new Thread(this,threadName);
        t.start();//run();
        return waifuList;
    }

    }



