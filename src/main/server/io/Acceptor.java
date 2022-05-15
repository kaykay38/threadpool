package main.server.io;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import main.server.util.JobQueue;
import main.server.util.SocketQueue;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description Listen to client connection and maintains a socket connection queue
 * @create 2022-05-09 23:52
 */
public class Acceptor extends Thread {
    private SocketQueue socketQueue; // sockets that are connecting to the server
    private JobQueue jobQueue;
    private ServerSocket listener;
    private int maxConnections = 50;

    /**
     * server.io.Acceptor.Acceptor():
     * @date 2022/5/10~15:55
     * @param listener the server socket used to listen for incoming client connections
     * @return
     */
    public Acceptor(ServerSocket listener, JobQueue jobQueue) {
        this.socketQueue = new SocketQueue(maxConnections);
        this.jobQueue = jobQueue;
        this.listener = listener;
    }

    /**
     * server.io.Acceptor.Acceptor():
     * @date 2022/5/10~15:55
     * @param listener the server socket used to listen for incoming client connections
     * @param maxConnections the maximum number of connections that can be queued
     */
    public Acceptor(ServerSocket listener, JobQueue jobQueue, int maxConnections) {
        this.socketQueue = new SocketQueue(maxConnections);
        this.jobQueue = jobQueue;
        this.listener = listener;
    }

    /**
     * server.io.Acceptor.run():
     * keep listening for all incoming connections and <br>
     * this thread will be blocked when there's no new connection coming in
     * @date 2022/5/10~10:19
     */
    @Override
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = listener.accept();
                if (jobQueue.size() == jobQueue.getCapacity()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("The server is currently busy, please connect later!!!!!!!!!!!!!!!!!!!");
                    socket = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (socket != null) {
                synchronized (socketQueue) {
                    socketQueue.enqueue(socket);
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * server.io.Acceptor.getSocketQueue():
     * get the socket queue
     * @date 2022/5/10~10:23
     * @return SocketQueue
     */
    public SocketQueue getSocketQueue() {
        return socketQueue;
    }
}