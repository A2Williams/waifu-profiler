package profiler;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Waifu {

    private Image waifuImage;
    private String waifuImageURL;
    private String waifuName;
    private double waifuRating;
    private String waifuYESNO;

    public Waifu(String url, String wname) throws IOException{ //Throws an exception if the URL fails.
        Image temp = new Image(url);
        this.waifuImageURL = url;
        this.waifuName = wname;
        //set the image as the URL, just to make things a little more simple.
        URL waifuSrc = new URL(url);
        URLConnection sourceConn = waifuSrc.openConnection();
        sourceConn.setDoInput(true);
        sourceConn.setDoOutput(false);
        sourceConn.addRequestProperty("User-Agent", "WaifuApp/0.5"); //Just to ensure we can actually access the image.
        InputStream inStream = sourceConn.getInputStream();
        Image waifu = SwingFXUtils.toFXImage(ImageIO.read(inStream), null);
        setWaifuImage(waifu);
        setWaifuImageURL(url);
        setWaifuName(wname);
    }

    public void setWaifuImage(Image wi){

        this.waifuImage = wi;
    }
    public Image getWaifuImage(){

        return this.waifuImage;
    }

    public void setWaifuImageURL(String waifuURL){

        this.waifuImageURL = waifuURL;
    }

    public String getWaifuImageURL() {
        return waifuImageURL;
    }

    public void setWaifuName(String name){
        this.waifuName = name;
    }

    public String getWaifuName() {
        return waifuName;
    }

    public void setWaifuRating(double rate){
        this.waifuRating = rate;
    }

    public double getWaifuRating() {
        return waifuRating;
    }

    public void setWaifuYESNO(String waifuYESNO) {
        this.waifuYESNO = waifuYESNO;
    }

    public String getWaifuYESNO() {
        return waifuYESNO;
    }
}
