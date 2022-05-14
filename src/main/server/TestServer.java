package main.server;

import main.server.io.Acceptor;
import main.server.io.Handler;
import main.server.process.JobQueue;
import main.server.process.ThreadManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description Currently just testing for MyThreadPool (not for acceptor and handler)
 * @create 2022-05-10 17:32
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        System.out.println("The test server is running.");
        ServerSocket listener = new ServerSocket(9898);
        // Acceptor acceptor = new Acceptor(listener);
        JobQueue jobQueue = new JobQueue(50);
        ThreadManager manager = new ThreadManager(jobQueue);
        Deque<Socket> socketQueue = new ArrayDeque<>();
        // Handler handler = new Handler(socketQueue, jobQueue);

        try {
            Socket socket = listener.accept();
            // acceptor.start();
            socketQueue.add(socket);
            manager.start();
            Thread.sleep(1000L);
            // handler.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }
}
