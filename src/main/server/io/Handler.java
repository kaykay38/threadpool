package server.io;

import server.process.MyMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.Deque;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description handle reading requests from all sockets
 * @create 2022-05-09 23:57
 */
public class Handler extends Thread{
    private Deque<Socket> socketQueue;
    private MyMonitor jobQueue;
    private HandlerMonitor monitor;
    /**
     * server.io.Handler.Handler():
     * constructor
     * @date 2022/5/10~10:20
     * @param socketQueue the socketQueue grabbed from Acceptor
     * @return
     */
    public Handler(Deque<Socket> socketQueue, MyMonitor jobQueue, HandlerMonitor monitor) {
        this.socketQueue = socketQueue; // ATTENTION!!!!!NOT THREAD SAFE!!!
        this.jobQueue = jobQueue;
        this.monitor = monitor;
    }

    /**
     * server.io.Handler.doRead():
     * scan through input streams of all sockets in the socket queue <br>
     * and enqueue all tasks into the job queue (only 1 time)
     * @date 2022/5/10~11:22
     * @param
     * @return void
     */
    public void doRead() {
        BufferedReader in = null;
        String input = null;
        for (Socket socket : socketQueue) {
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                if (in.ready())
                    input = in.readLine();
                if (input != null) {
                    jobQueue.enqueue(socket, input);
                    System.out.println(input);
                    // System.out.println("handler reading!!");
                }

            } catch (SocketException e) {
                socketQueue.remove(socket);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            doRead();

        }
    }

    public void sendReader(BufferedReader reader) {
        monitor.setReader(reader);
    }


}
