package main.server.process;


import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;


/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description The job queue for threads, Shared Object, Producer Consumer Model
 * @create 2022-05-08 15:55
 */
public class JobQueue {// job queue

    private int capacity;
    private Deque<MyJob> q; // job queue object

    /**
     * server.process.JobQueue.JobQueue():
     * constructor
     * @date 2022/5/8~18:51
     * @param capacity max capacity for a job queue
     * @return
     */
    public JobQueue(int capacity) {
        this.capacity = capacity;
        this.q = new ArrayDeque<>();
    }

    /**
     * server.process.JobQueue.enqueue():
     * This method enqueues a job to the job queue. <br>
     * @param clientSocket socket which issues this job
     * @param instruction input instruction in a string form
     * @return void
     * @date 2022/5/8~17:29
     */
    public synchronized void enqueue(Socket clientSocket, String instruction) {
        while (q.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // create a job
        MyJob job = new MyJob(clientSocket, instruction);

        // put the job into queue
        q.addLast(job);

        notifyAll();
    }

    /**
     * server.process.JobQueue.dequeue():
     * This method dequeues a job from the queue
     * (NEED FURTHER IMPLEMENTATION TO MAKE IT THREAD SAFE)
     * @date 2022/5/8~18:52
     * @param
     * @return server.process.MyJob
     */
    public synchronized MyJob dequeue() {
        while (q.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // remove a job from queue
        MyJob job = q.removeFirst();

        notifyAll();

        return job;
    }

    /**
     * server.process.JobQueue.getQueueSize():
     * return the size of job queue
     * @date 2022/5/8~23:12
     * @param
     * @return int
     */
    public synchronized int size() {
        return q.size();
    }
}
