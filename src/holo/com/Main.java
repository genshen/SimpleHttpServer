package holo.com;

import holo.com.tools.Network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(Network.HttpPort);
            System.out.println("Listening for connection on port 8080 ....");
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                (new ServerThread(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
