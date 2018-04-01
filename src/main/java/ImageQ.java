import java.io.*;
import java.net.*;
import java.util.LinkedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.*;

import javax.imageio.ImageIO;

public class ImageQ{
    private static int BUFFER_SIZE; //How few images before we start adding more to the queue?
    private static int QUEUE_MAX; //How many images before we refuse to queue any more images?
    private static int ENQUEUE_INC;//How many pages do we increment by every time we enqueue more waifus?
    private static String url = "https://danbooru.donmai.us/posts.json?tags="; //the first part of the query to danbooru
    private static String query; //the second part of the query to danbooru
    //in both the above instances, program inputs will be placed in the input area"
    private URL source;
    private int onpage;
    private LinkedList<JSONObject> waifuList; //queues are FIFO, so all waifuQueus will be automatically sorted by date.

    public ImageQ ()
    {
        //Assigns default values in case the user doesn't want to supply them.
        this.BUFFER_SIZE = 5;
        this.QUEUE_MAX = 20;
        this.query = "&limit="+this.QUEUE_MAX+"&page=";
        this.ENQUEUE_INC = 1;
        this.onpage=1;
    }

    public ImageQ(int buffer, int maxsize)//for those that may want to pull varying amounts of waifu
    {
        this.BUFFER_SIZE = buffer;
        this.QUEUE_MAX = maxsize;
        this.url = "https://danbooru.donmai.us/posts.json?limit="+this.QUEUE_MAX+"&page=";
    }

    public ImageQ(int buffer, int maxsize, int step)//if you're pulling more than 20 waifus per enqueue, it's gonna create duplicates.
    {
        this.BUFFER_SIZE = buffer;
        this.QUEUE_MAX = maxsize;
        this.url = "https://danbooru.donmai.us/posts.json?limit="+this.QUEUE_MAX+"&page=";
        this.ENQUEUE_INC = step;
    }

    public LinkedList<JSONObject> getList() throws Exception//Since most usage of getList() will be to pop, I need to add the buffer logic in here.
    {
        if (this.source == null || waifuList == null)
        {
            System.err.println("Error while executing getQueue(): URL was never initialized or list was null! Are you sure you called search() beforehand?");
            return null;
        }
        else {
            if (this.waifuList.size() < this.BUFFER_SIZE && this.waifuList.size() < this.QUEUE_MAX)//in theory, the second part of this statement should never be true.
            {
                this.update();
            }
            return this.waifuList;
        }
    }

    public void search(String character) throws Exception//Searches danbooru for jsons of posts tagged with the character tags provided.
    {
        this.source = new URL((this.url + character + this.query + this.onpage)); //Appends all the messy strings into one messy URL.
        this.waifuList = new LinkedList();
        update();
        if (this.waifuList.size() < QUEUE_MAX)
        {
            update();
        }
    }

    private void update() throws Exception //update is VERY similar to what i want to do with search so i just call it in search.
    {
        if (this.source != null) {
            URLConnection sourceConn = this.source.openConnection(); //opens a connection to the json list.
            sourceConn.setDoInput(true);
            sourceConn.setDoOutput(false);

            InputStream inStream = sourceConn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream)); //Creates a buffered reader to read data from the connection to danbooru.

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)//Reads in all the JSON data
            {
                buffer.append(line);
            }
            String theJsons = buffer.toString();
            JSONArray data = new JSONArray(theJsons);
            for (int i = 0; i < data.length(); i++) //Because danbooru only lets you search 2 tags at once I'll do manual pruning.
            {
                if (data.getJSONObject(i).getString("tag_string_general").contains("swimsuit") == false && data.getJSONObject(i).getString("tag_string_general").contains("underwear") == false && data.getJSONObject(i).getString("tag_string_general").contains("solo"))
                {
                    if (data.getJSONObject(i).getString("rating").contains("s")) {
                        this.waifuList.add(data.getJSONObject(i));
                    }
                }
            }
            inStream.close();
            this.onpage += this.ENQUEUE_INC;
            if (this.waifuList.size() < QUEUE_MAX) {
                update();
            }
        }
    }

    public Image getNextWaifu() throws Exception//Gets the image, then pops the JSON it took the image from. Use in conjunction with getList() for any UI stuff.
    {
        Image waifu = null;
        if (waifuList != null)
        {
            URL waifuSrc = new URL(this.waifuList.get(0).getString("large_file_url"));
            waifu = SwingFXUtils.toFXImage(ImageIO.read(waifuSrc), null);
            this.waifuList.pop();
            if (this.waifuList.size() < this.BUFFER_SIZE && this.waifuList.size() < this.QUEUE_MAX)//in theory, the second part of this statement should never be true.
            {
                this.update();
            }
        }
        else
        {
            System.err.println("Error while executing getNextWaifu(): getNextWaifu() was called before initial search()!");
        }
        return waifu;
    }
}
