package main.server.process;

import java.io.IOException;
import java.io.PrintWriter;

import main.server.util.Instruction;
import main.server.util.JobQueue;
import main.server.util.TimeUtil;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 15:33
 */
public class ThreadPool {

    private int maxCapacity;
    private int numberThreadsRunning;
    private int prevNumberThreadsRunning;
    private WorkerThread workerThreads[]; // stores the worker thread references
    private volatile boolean isStopped; // used to receive a stop signal from main thread
    private JobQueue jobQueue; //and the main server thread

    /**
     * server.process.MyThreadPool.MyThreadPool():
     * constructor
     *
     * @param maxCapacity maximum number of threads in the pool
     * @param jobQueue    shared by all WorkerThread in the pool and ThreadManager
     * @date 2022/5/8~18:57
     */
    public ThreadPool(int maxCapacity, JobQueue jobQueue) {
        this.maxCapacity = maxCapacity;
        this.numberThreadsRunning = 0;
        this.workerThreads = new WorkerThread[maxCapacity];
        this.isStopped = false;
        this.jobQueue = jobQueue;
    }

    // each Worker will grab a job in the jobQueue for
    // processing if there are available jobs in the jobQueue.
    private class WorkerThread extends Thread {
        private int id;
        private volatile boolean finished = false;

        public WorkerThread(int id) {
            this.id = id;
        }

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
                // dequeue will be blocked if there are no jobs to grab
                Job job = jobQueue.dequeue(); // grab a job from queue

                try {
                    // create the output stream for sending back message
                    PrintWriter out = new PrintWriter(job.getClientSocket().getOutputStream(), true);
                    try {
                        // get the result by executing instruction
                        double result = Instruction.execute(job.getInstruction());

                        log("WorkerThread-" + id + ": processed " + job.getInstructionId() + " '" + job.getInstruction() + "' from " + job.getClientId() + " at " + TimeUtil.getCurrentTime());

                        // sleep for a while to simulate the processing time
                        // to allow jobQueue to be filled up
                        Thread.sleep(200L);

                        // send message back to client
                        out.println(result);
                        // log("Running threads: " + numberThreadsRunning);

                    } catch (IllegalArgumentException e) { // INVALID
                        out.println(e.getMessage());
                    } catch (InterruptedException e) { // if the thread is interrupted
                        e.printStackTrace();
                        out.println("Thread interrupted");
                    } catch (RuntimeException e) { // KILL
                        log("KILL instruction received at " + job.getTimeStamp());
                        out.println(e.getMessage() + "\n" + "Server is shutting down");
                        isStopped = true;
                        finished = true;
                        System.exit(0);
                    }
                } catch (IOException e) { // failure to get output stream
                    e.printStackTrace();
                }
            }
            log("WorkerThread-" + id + ": terminated at " + TimeUtil.getCurrentTime());
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
        numberThreadsRunning = 5;
        for (int i = 0; i < numberThreadsRunning; i++) {
            workerThreads[i] = new WorkerThread(i);
            workerThreads[i].start();
        }
        log("ThreadPool: started with " + numberThreadsRunning + " threads at " + TimeUtil.getCurrentTime());
    }

    /**
     * server.process.MyThreadPool.increaseThreadsInPool():
     * double the threads in pool according to threshold
     * @date 2022/5/8~22:44
     * @param
     * @return void
     */
    public void increaseThreads() {
        for (int i = numberThreadsRunning; i < 2 * numberThreadsRunning; i++) {
            workerThreads[i] = new WorkerThread(i);
            workerThreads[i].start();
        }

        // update current thread #
        prevNumberThreadsRunning = numberThreadsRunning;
        numberThreadsRunning *= 2;
        log("ThreadPool: Threads doubled from " + prevNumberThreadsRunning + " to " + numberThreadsRunning + " threads at " + TimeUtil.getCurrentTime());
    }

    /**
     * server.process.MyThreadPool.decreaseThreadsInPool():
     * halve the threads in pool according to threshold
     * @date 2022/5/8~22:44
     * @param
     * @return void
     */
    public void decreaseThreads() {
        for (int i = numberThreadsRunning - 1; i >= numberThreadsRunning / 2; i--) {
            workerThreads[i].stopThread();
        }

        // update current thread #
        prevNumberThreadsRunning = numberThreadsRunning;
        numberThreadsRunning /= 2;
        log("ThreadPool: Threads decreased from " + prevNumberThreadsRunning + " to " + numberThreadsRunning + " threads at " + TimeUtil.getCurrentTime());
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

		for (int i = 0; i < numberThreadsRunning; i++) {
		    workerThreads[i].stopThread();
        }

		// clear # of threads
		numberThreadsRunning = 0;
        log("ThreadPool: All threads terminated at " + TimeUtil.getCurrentTime());
    }

    /**
     * server.process.MyThreadPool.numberThreadsRunning():
     * get # of current running threads
     * @date 2022/5/8~23:13
     * @param
     * @return int
     */
    public int getNumberThreadsRunning() {
        return numberThreadsRunning;
    }

    /**
     * server.process.MyThreadPool.getStopSignal():
     * get the stop signal
     * @date 2022/5/11~21:42
     * @return int
     */
    public boolean isStopped() {
        return isStopped;
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

    /**
     * Logs a simple message. In this case, we just write the
     * message to the server applications standard output.
     */
    private void log(String message) {
        System.out.println(message);
    }
}
