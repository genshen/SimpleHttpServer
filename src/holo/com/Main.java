package holo.com;

import holo.com.tools.Network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService fixedThreanPool = Executors.newFixedThreadPool(2);
        try {
            ServerSocket server = new ServerSocket(Network.HttpPort);
            System.out.println("Listening for connection on port 8080 ....");
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                fixedThreanPool.execute(new ServerThread(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
