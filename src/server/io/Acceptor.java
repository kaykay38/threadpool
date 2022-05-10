package server.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description Listen to client connection and maintains a socket connection queue
 * @create 2022-05-09 23:52
 */
public class Acceptor extends Thread{
    private Deque<Socket> socketQueue;
    private ServerSocket listener;

    public Acceptor(ServerSocket listener) {
        this.socketQueue = new ArrayDeque<Socket>();
        this.listener = listener;
    }

    /**
     * server.io.Acceptor.run():
     * keep listening for all incoming connections and <br>
     * this thread will be blocked when there's no new connection coming in
     * @date 2022/5/10~10:19
     * @param
     * @return void
     */
    @Override
    public void run() {
        while (Thread.currentThread().isInterrupted()) {
            Socket socket = null;
            try {
                socket = listener.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            socketQueue.add(socket);
        }
    }

    /**
     * server.io.Acceptor.getSocketQueue():
     * get the socket queue
     * @date 2022/5/10~10:23
     * @param
     * @return java.util.Deque<java.net.Socket>
     */
    public Deque<Socket> getSocketQueue() {
        return socketQueue;
    }
}
