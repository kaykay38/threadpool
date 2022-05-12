package server;

import server.io.Acceptor;
import server.io.Handler;
import server.io.HandlerMonitor;
import server.process.MyMonitor;
import server.process.ThreadManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-11 17:36
 */
public class TestServer2 {
    public static void main(String[] args) throws IOException {
        System.out.println("The test server is running.");
        ServerSocket listener = new ServerSocket(9898, 500);
        Acceptor acceptor = new Acceptor(listener);
        MyMonitor jobQueue = new MyMonitor(50);
        ThreadManager manager = new ThreadManager(jobQueue);
        // Deque<Socket> testQueue = new ArrayDeque<Socket>();
        HandlerMonitor monitor = new HandlerMonitor();
        Handler handler = new Handler(acceptor.getSocketQueue(), jobQueue, monitor);

        try {
            // monitor.start();
            acceptor.start();
            manager.start();
            handler.start();
            Thread.sleep(3000L);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
