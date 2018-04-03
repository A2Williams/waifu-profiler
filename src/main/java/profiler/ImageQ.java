package profiler;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ImageQ{
    private LinkedList<Waifu> waifuList;
    private int SIZE;

    public ImageQ (int size, String hostname, int port) throws IOException
    {
        this.SIZE = size;

        for (int i = 0; i < size; i++)
        {
            Socket input = new Socket(hostname, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(input.getInputStream()));
            PrintWriter out = new PrintWriter(input.getOutputStream());
            out.println("GET");
            out.flush();
            String waifu = in.readLine();
            String[] waifus = waifu.split(",");
            waifuList.add(new Waifu(waifus[0],waifus[1]));
            in.close();
            out.close();
            input.close();
        }

    }

    public int size()
    {
        return this.SIZE;
    }

    public Waifu getNextWaifu()
    {
        this.SIZE--;
        return this.waifuList.pop();
    }
}
