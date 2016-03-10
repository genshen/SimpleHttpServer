package holo.com;

import holo.com.tools.Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService fixedThreanPool = Executors.newFixedThreadPool(2);
        try {
            ServerSocket server = new ServerSocket(Network.HttpPort);
            System.out.println("Listening for connection to" + Network.HOST + ":8888 ....");
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                fixedThreanPool.execute(new ServerThread(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
