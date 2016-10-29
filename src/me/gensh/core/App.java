package me.gensh.core;

import me.gensh.core.response.render.DefaultHtmlRenderInstance;
import me.gensh.router.Router;
import me.gensh.core.utils.Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gensh on 2016/10/27.
 */
public class App {
    private ExecutorService fixedThreadPool;

    public App() {
        fixedThreadPool = Executors.newFixedThreadPool(2);
        DefaultHtmlRenderInstance.initConfig(); //config template engine
        new Router();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Config.HttpPort);
            System.out.println("Listening for connection to" + Network.HOST + ":" + Config.HttpPort + " ....");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                fixedThreadPool.execute(new ServerThread(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
