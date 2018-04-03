package profiler;


public class Waifu {

    private String waifuImageURL;
    private String waifuName;
    private float waifuRating;
    private float waifuTotalVotes;


    public Waifu(String url, String wname){
        this.waifuImageURL = url;
        this.waifuName = wname;
    }

    public String toString()
    {
        String output=this.waifuImageURL+","+this.waifuName;
        return output;
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

    public void setWaifuRating(int rate){
        this.waifuRating = rate;
    }

    public double getWaifuRating() {
        return waifuRating;
    }


    public void userSaysYes(){
        this.waifuRating=this.waifuRating+1;
        this.waifuTotalVotes=this.waifuTotalVotes+1;
    }
    public void userSaysNo(){
        this.waifuTotalVotes=this.waifuTotalVotes+1;
    }
    public float calculatePercentage(){
        float percentage=this.waifuRating / this.waifuTotalVotes;
        return percentage;
    }
}
