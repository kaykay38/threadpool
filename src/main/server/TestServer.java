package main.server;

import main.server.io.Acceptor;
import main.server.io.Handler;
import main.server.process.MyMonitor;
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
            Thread.sleep(10000L);
            // add some jobs
            for (int i = 0; i < 100; i++) {
//                jobQueue.enqueue(socket, "ADD,10,20");

                jobQueue.enqueue(socket, "MUL,1009240294,2092034923");

//                Thread.sleep(2000L);
//                if (i == 3)
//                    jobQueue.enqueue(socket, "KILL,0,0");

            }


            // handler.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }
}
