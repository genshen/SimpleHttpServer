package holo.com.tools;

import holo.com.request.HttpReader;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ¸ùÉî on 2016/2/15.
 */
public class Network {
    public static int HttpPort = 8080;

    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "localhost";
    }
}
