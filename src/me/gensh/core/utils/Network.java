package me.gensh.core.utils;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by cgs on 2016/2/15.
 */
public class Network {
    public static String HOST = getLocalIp();

    public static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress() ) { //&& InetAddressUtils.isIPv4Address(ip.getHostAddress())
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
