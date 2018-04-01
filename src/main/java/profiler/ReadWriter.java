package profiler;

import java.io.*;
import java.util.Scanner;

public class ReadWriter {
    public static void ReadWriter(){}

    public static void recordTag(String inp) {
        File file = new File("pref.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            bw.write(inp);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Don't know if this is neccesary
    public static void saveWaifu(String urlinp,String nameinp){
        File file = new File("bestgirls.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            bw.write(urlinp+","+nameinp);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean scanForTags(String requestedWord){
        File file = new File("pref.txt");
        try {
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals(requestedWord)) {
                    return true;
                }
            }
            sc.close();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
     public static boolean scanForWaifu(String requestedWord){
        File file = new File("bestgirls.txt");
        try {
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals(requestedWord)) {
                    return true;
                }
            }
            sc.close();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
