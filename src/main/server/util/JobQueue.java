package main.server.util;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import main.server.process.Job;


/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description The job queue for threads, Shared Object, Producer Consumer Model
 * @create 2022-05-08 15:55
 */
public class JobQueue implements Iterable<Job> {// job queue

    private int capacity;
    private Deque<Job> q; // job queue object

    /**
     * server.process.JobQueue.JobQueue():
     * constructor
     * @date 2022/5/8~18:51
     * @return
     */
    public JobQueue() {
        this.capacity = 50;
        this.q = new ArrayDeque<>();
    }

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
    public synchronized void enqueue(Socket clientSocket, String instruction, String instructionId, String clientId) {
        while (q.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // create a job
        Job job = new Job(clientSocket, instruction, instructionId, clientId);

        // put the job into queue
        q.addLast(job);

        // System.out.println("JobQueue: enqueued " + instructionId + " '" + job.getInstruction() + "' " + "from " + clientId + " at " + job.getTimeStamp());
        // System.out.println("JobQueue: size is " + q.size());

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
    public synchronized Job dequeue() {
        while (q.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // remove a job from queue
        Job job = q.removeFirst();

        // System.out.println("JobQueue: dequeued " + job.getInstructionId() + " '" + job.getInstruction() + "' " + "from " + job.getClientId() + " at " + job.getTimeStamp());
        // System.out.println("JobQueue: size is " + q.size());

        notifyAll();

        return job;
    }

    /**
     * server.process.JobQueue.getCapcity():
     * This method returns the maximum capacity of the job queue
     * @return int
     */
    public synchronized int getCapacity() {
        return capacity;
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

    /**
     * @return java.util.Iterator<server.process.MyJob>
     */
    @Override
    public synchronized Iterator<Job> iterator() {
        return q.iterator();
    }

    /**
     * @param action The action to be performed for each job in the queue
     */
    @Override
    public synchronized void forEach(Consumer<? super Job> action) {
        q.forEach(action);
    }

    /**
     * @return A Spliterator over the elements in the job queue
     */
    @Override
    public synchronized Spliterator<Job> spliterator() {
        return q.spliterator();
    }
}