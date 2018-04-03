package profiler;

import java.io.*;
import java.net.*;

public class ImageQ{
    private LinkedList<Waifu> waifuList;
    private int SIZE;

    public ImageQ (int size, Socket input) throws IOException
    {
        this.SIZE = size;
        BufferedReader in = new BufferedReader(new InputStreamReader(input.getInputStream()));
        PrintWriter out = new PrintWriter(input.getOutputStream());
        out.print("GET "+ String.valueOf(size));
        out.flush();
        for (int i = 0; i < size; i++)
        {
            String waifu = in.readLine();
            String[] waifus = waifu.split(",");
            waifuList.add(new Waifu(waifus[0],waifus[1]));
        }
        in.close();
        out.close();
        input.close();
    }

    public int Size()
    {
        return this.SIZE;
    }

    public Waifu getNextWaifu()
    {
        this.SIZE--;
        return this.waifuList.pop();
    }
}
