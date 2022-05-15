import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;


/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description The Socket queue for client sockets, Shared Object, Producer Consumer Model
 * @create 2022-05-13
 */
public class SocketQueue implements Iterable<Socket> {// job queue

    private int capacity;
    private Deque<Socket> q; // job queue object

    /**
     * server.process.JobQueue.JobQueue():
     * constructor
     * @date 2022/5/8~18:51
     * @param capacity max capacity for a socket queue
     */
    public SocketQueue(int capacity) {
        this.capacity = capacity;
        this.q = new ArrayDeque<>();
    }

    /**
     * server.process.SocketQueue.enqueue():
     * This method enqueues a client socket to the socket queue. <br>
     * @param clientSocket socket which issues this job
     */
    public synchronized void enqueue(Socket clientSocket) {
        while (q.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // add a client socket into queue
        q.addLast(clientSocket);

        notifyAll();
    }

    /**
     * server.process.SocketQueue.dequeue():
     * This method dequeues a client socket from the queue
     * @return Socket
     */
    public synchronized Socket dequeue() {
        while (q.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // remove a socket from queue
        Socket clientSocket = q.removeFirst();

        notifyAll();

        return clientSocket;
    }

    /**
     * server.process.SocketQueue.dequeue():
     * This method dequeues the specified client socket from the queue
     * @param socket
     * @return Socket
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
     * server.process.SocketQueue.getQueueSize():
     * return the size of socket queue
     * @return int
     */
    public synchronized int size() {
        return q.size();
    }

    /**
     * @return java.util.Iterator<server.process.MyJob>
     */
    @Override
    public synchronized Iterator<Socket> iterator() {
        return q.iterator();
    }

    /**
     * @param action The action to be performed for each socket in the queue
     */
    @Override
    public synchronized  void forEach(Consumer<? super Socket> action) {
        q.forEach(action);
    }

    /**
     * @return A Spliterator over the elements in the socket queue
     */
    @Override
    public synchronized Spliterator<Socket> spliterator() {
        return q.spliterator();
    }
}