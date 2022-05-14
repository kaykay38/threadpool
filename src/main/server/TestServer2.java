package main.server;

import main.server.process.JobQueue;
import main.server.io.Acceptor;
import main.server.io.Handler;
import main.server.process.ThreadManager;

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
        JobQueue jobQueue = new JobQueue(50);
        Acceptor acceptor = new Acceptor(listener, jobQueue);
        ThreadManager manager = new ThreadManager(jobQueue);
        Handler handler = new Handler(acceptor.getSocketQueue(), jobQueue);

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
