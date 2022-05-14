package main.server.process;

import main.utility.InstructionSet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 15:33
 */
public class MyThreadPool {

    private int maxCapacity;
    private int actualNumberThreads;
    private WorkerThread holders[];    // stores the worker thread references
    private volatile boolean stopped;  // used to receive a stop signal from main thread
    private JobQueue jobQueue; //and the main server thread

    /**
     * server.process.MyThreadPool.MyThreadPool():
     * constructor
     *
     * @param maxCapacity maximum number of threads in the pool
     * @param jobQueue    shared by all WorkerThread in the pool and ThreadManager
     * @date 2022/5/8~18:57
     */
    public MyThreadPool(int maxCapacity, JobQueue jobQueue) {
        this.maxCapacity = maxCapacity;
        this.actualNumberThreads = 0;
        this.holders = new WorkerThread[maxCapacity];
        this.stopped = false;
        this.jobQueue = jobQueue;
    }

    private class WorkerThread extends Thread {
        // each Worker will grab a job in the jobQueue for
        // processing if there are available jobs in the jobQueue.
        private volatile boolean finished = false;

        /**
         * server.process.MyThreadPool.WorkerThread.stopThread():
         * stop a thread gracefully
         * @date 2022/5/8~22:58
         * @param
         * @return void
         */
        public void stopThread() {
            finished = true;
        }


        @Override
        public void run() {
            while (!finished) {// wait for a stop signal

                System.out.println(Thread.currentThread().getName() + " is working...");
                // dequeue will be blocked if there are no jobs to grab
                MyJob job = jobQueue.dequeue(); // grab a job from queue

                try {
                    // create the output stream for sending back message
                    PrintWriter out = new PrintWriter(job.getClientSocket().getOutputStream(), true);
                    try {
                        // get the result by executing instruction
                        double result = InstructionSet.execute(job.getInstruction());

                        Thread.sleep(100);

                        // send message back to client
                        out.println(result);
                        System.out.println("Running threads: " + actualNumberThreads);

                    } catch (NumberFormatException e) {// failure to get output stream
//                        e.printStackTrace();
                        out.println("Invalid numbers");
                    } catch (IllegalArgumentException e1) { // illegal instructions
                        e1.printStackTrace();
                        out.println(e1.getMessage());
                    } catch (RuntimeException e2) {// KILL
                        e2.printStackTrace();
                        out.println(e2.getMessage());
                        stopped = true;
                        finished = true;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {// failure to get output stream
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " is terminated");
        }
    }

    /**
     * server.process.MyThreadPool.startPool():
     * start all available threads in the pool and Worker threads start to process jobs
     * @date 2022/5/8~22:40
     * @param
     * @return void
     */
    public void startPool() {
        // begin with 5 threads
        actualNumberThreads = 5;
        for (int i = 0; i < actualNumberThreads; i++) {
            holders[i] = new WorkerThread();
            holders[i].start();
        }
    }


    /**
     * server.process.MyThreadPool.increaseThreadsInPool():
     * double the threads in pool according to threshold
     * @date 2022/5/8~22:44
     * @param
     * @return void
     */
    public void increaseThreads() {

        for (int i = actualNumberThreads; i < 2 * actualNumberThreads; i++) {
            holders[i] = new WorkerThread();
            holders[i].start();
        }

        // update current thread #
        actualNumberThreads *= 2;

    }


    /**
     * server.process.MyThreadPool.decreaseThreadsInPool():
     * halve the threads in pool according to threshold
     * @date 2022/5/8~22:44
     * @param
     * @return void
     */
    public void decreaseThreads() {
        for (int i = actualNumberThreads - 1; i >= actualNumberThreads / 2; i--) {
            holders[i].stopThread();
        }

        // update current thread #
        actualNumberThreads /= 2;

    }

    /**
     * server.process.MyThreadPool.stopPool():
     * terminate all threads in the pool gracefully <br>
     * all threads in pool terminate when a command KILL
     * is sent through the client to the server.
     * @date 2022/5/8~22:42
     * @param
     * @return void
     */
    public void stopPool() {

		for (int i = 0; i < actualNumberThreads; i++) {
		    holders[i].stopThread();
        }

		// clear # of threads
		actualNumberThreads = 0;
        System.out.println("All threads are terminated");
    }

    /**
     * server.process.MyThreadPool.numberThreadsRunning():
     * get # of current running threads
     * @date 2022/5/8~23:13
     * @param
     * @return int
     */
    public int getNumberThreadsRunning() {
        return actualNumberThreads;
    }

    /**
     * server.process.MyThreadPool.getStopSignal():
     * get the stop signal
     * @date 2022/5/11~21:42
     * @return int
     */
    public boolean getStopSignal() {
        return stopped;
    }

    /**
     * server.process.MyThreadPool.getMaxCapacity():
     * get max capacity of the pool
     * @date 2022/5/11~21:42
     * @return int
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }
}
