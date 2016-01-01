package holo.com.require;


/**
 * Created by ¸ùÉî on 2015/12/31.
 */
public class Referer {
    String fileroute = null;

    public Referer(String s) {
        if(s == null) fileroute = "index.html";
        fileroute = s.split("[?]", 1)[0];
        System.out.println(s);
    }

    public String getRoute() {
        return this.fileroute;
    }
}
