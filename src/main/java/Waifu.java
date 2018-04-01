package java;


import javafx.scene.image.Image;

public class Waifu {

    private Image waifuImage;
    private String waifuImageURL;
    private String waifuName;
    private double waifuRating;
    private String waifuYESNO;

    public Waifu(String url, String wname){
        Image temp = new Image(url);
        this.waifuImageURL = url;
        this.waifuName = wname;
        setWaifuImage(temp);
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
