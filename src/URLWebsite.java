import java.util.ArrayList;

public abstract class URLWebsite {

    protected static String URL = "";

    public URLWebsite(String url) {
        URLWebsite.URL = url;
    }

    public static String getURL() {
        return URL;
    }

    // public static void setURL(String url) {   //nie jest to potrzebne, bo URL jest ustawiany w konstruktorze
    //     // this.URL = url;
    // }

    public abstract ArrayList<GameInfo> addNewGame();

}
