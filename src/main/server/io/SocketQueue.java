package main.server.io;



import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-13 18:03
 */
public class SocketQueue implements Iterable<Socket>{
    private Deque<Socket> q; // job queue object
    private int capacity;
    /**
     * main.server.io.SocketQueue.SocketQueue():
     * constructor
     * @date 2022/5/13~18:04
     *
     *
     */
    public SocketQueue(int capacity) {
        this.q = new ArrayDeque<Socket>();
        this.capacity = capacity;
    }

    /**
     * main.server.io.SocketQueue.enqueue():
     * This method enqueues a job to the socket queue
     * @date 2022/5/13~18:05
     * @param clientSocket
     * @return void
     */
    public synchronized void enqueue(Socket clientSocket) {
        while (q.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // put the job into queue
        q.addLast(clientSocket);

        notifyAll();
    }

    /**
     * main.server.io.SocketQueue.dequeue():
     * This method dequeues a job from the socket queue
     * @date 2022/5/13~18:12
     * @param
     */
    public synchronized void dequeue(Socket socket) {
        while (q.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // remove the socket from queue
        q.remove(socket);
        notifyAll();

    }

    /**
     * main.server.io.SocketQueue.size():
     * get the size of a socket queue
     * @date 2022/5/13~18:10
     * @param
     * @return int
     */
    public synchronized int size() {
        return q.size();
    }



    @Override
    public synchronized Iterator<Socket> iterator() {
        return this.q.iterator();
    }

    @Override
    public synchronized void forEach(Consumer<? super Socket> action) {

    }
}
