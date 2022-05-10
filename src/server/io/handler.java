package server.io;

import server.process.MyMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Deque;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description handle io requests from all sockets
 * @create 2022-05-09 23:57
 */
public class handler extends Thread{
    private Deque<Socket> socketQueue;
    private MyMonitor jobQueue;
    /**
     * server.io.handler.handler():
     * constructor
     * @date 2022/5/10~10:20
     * @param socketQueue the socketQueue grabbed from Acceptor
     * @return
     */
    public handler(Deque<Socket> socketQueue, MyMonitor jobQueue) {
        this.socketQueue = socketQueue; // ATTENTION!!!!!NOT THREAD SAFE!!!
        this.jobQueue = jobQueue;
    }

    /**
     * server.io.handler.doRead():
     *
     * @date 2022/5/10~11:22
     * @param
     * @return void
     */
    public void doRead() {
        BufferedReader in = null;
        for (Socket socket : socketQueue) {
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String input = in.readLine();
                if (input != null)
                    jobQueue.enqueue(socket.toString(), input);
            } catch (IOException e) {
                e.printStackTrace();
            }




        }

    }

    public void doWrite() {
        PrintWriter out;
        for (Socket socket: socketQueue) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
