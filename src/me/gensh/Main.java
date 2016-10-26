package me.gensh;

import me.gensh.tools.Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        try {
            ServerSocket serverSocket = new ServerSocket(Network.HttpPort);
            System.out.println("Listening for connection to" + Network.HOST + ":8888 ....");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                fixedThreadPool.execute(new ServerThread(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
