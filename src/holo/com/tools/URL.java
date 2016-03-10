package holo.com.tools;

/**
 * Created by cgs on 2016/2/19.
 */
public class URL {
    static String Base = "http://"+ Network.HOST + (Network.HttpPort == 80 ? "" : ":" + Network.HttpPort);

    public static String url(String controller, String action) {
        return Base + "/" + controller + "/" + action + ".html";
    }
}
