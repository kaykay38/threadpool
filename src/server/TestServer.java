package server;

import server.io.Acceptor;
import server.io.Handler;
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
 * @create 2022-05-10 17:32
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        System.out.println("The capitalization server is running.");
        ServerSocket listener = new ServerSocket(9898);
        // Acceptor acceptor = new Acceptor(listener);
        MyMonitor jobQueue = new MyMonitor(50);
        ThreadManager manager = new ThreadManager(jobQueue);
        Deque<Socket> testQueue = new ArrayDeque<Socket>();
        Handler handler = new Handler(testQueue, jobQueue);

        try {
            // acceptor.start();
            Socket socket = listener.accept();
            testQueue.add(socket);
            manager.start();
            handler.start();

        } finally {
            listener.close();
        }
    }
}
