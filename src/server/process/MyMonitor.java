package server.process;


import java.util.ArrayDeque;
import java.util.Deque;


/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description The job queue for threads, Shared Object, Producer Consumer Model
 * @create 2022-05-08 15:55
 */
public class MyMonitor {// job queue

    private int capacity;
    private Deque<MyJob> q; // job queue object

    /**
     * server.process.MyMonitor.MyMonitor():
     * constructor
     * @date 2022/5/8~18:51
     * @param capacity max capacity for a job queue
     * @return
     */
    public MyMonitor(int capacity) {
        this.capacity = capacity;
        this.q = new ArrayDeque<MyJob>();
    }

    /**
     * server.process.MyMonitor.enqueue():
     * This method enqueues a job to the job queue.
     *
     * @param clientInfo socket which issues this job
     * @param instruction input instruction in a string form
     * @return void
     * @date 2022/5/8~17:29
     */
    public synchronized void enqueue(String clientInfo, String instruction) {

        // create a job
        MyJob job = new MyJob(clientInfo, instruction);

        // put the job into queue
        q.addLast(job);


    }

    /**
     * server.process.MyMonitor.dequeue():
     * This method dequeues a job from the queue
     * @date 2022/5/8~18:52
     * @param
     * @return server.process.MyJob
     */
    public synchronized MyJob dequeue() {

        // remove a job from queue
        MyJob job = q.removeFirst();

        return job;
    }

    /**
     * server.process.MyMonitor.getQueueSize():
     * return the size of job queue
     * @date 2022/5/8~23:12
     * @param
     * @return int
     */
    public int getQueueSize() {
        return q.size();
    }




}
