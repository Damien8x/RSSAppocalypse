package itc298final.com.rssappocolypse;

public class ListModel {

    private String title = "";
    private String description = "";
    private String rss = "";
    private String url = "";

    public String getRSS() { return this.rss; }

    public void setRSS( String rss ) { this.rss = rss; }

    public String getTitle() { return this.title; }

    public void setTitle( String title ) { this.title = title; }

    public String getDescription() { return this.description; }

    public void setDescription( String description ) { this.description = description;  }

    public String getURL() { return this.url; }

    public void setURL( String url ) { this.url = url; }

}