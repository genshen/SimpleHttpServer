package holo.com;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8080);
            System.out.println("Listening for connection on port 8080 ....");
            while (true) {
                Socket clientSocket = server.accept();
                new ServerThread(clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
