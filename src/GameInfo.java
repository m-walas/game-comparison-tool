public class GameInfo {

    private String imgURL;  // image for game
    private String name;    //The Witcher 3
    private String price;   // 50.00

    private String platform;        //pc
    private String distribution;    //steam
    private String language_version;    //polish
    private String pegi;    //PEGI 18
    private String mode;    //singleplayer
    private String premier; //2015-05-19

    public GameInfo() {
    }

    public GameInfo(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDistribution() {
        return this.distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getLanguage_version() {
        return this.language_version;
    }

    public void setLanguage_version(String language_version) {
        this.language_version = language_version;
    }

    public String getPegi() {
        return this.pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPremier() {
        return this.premier;
    }

    public void setPremier(String premier) {
        this.premier = premier;
    }
}
