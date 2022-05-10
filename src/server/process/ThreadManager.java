package server.process;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 15:54
 */
public class ThreadManager extends Thread {
    private MyMonitor jobQueue;
    private MyThreadPool pool;

    /**
     * server.process.ThreadManager.ThreadManager():
     * constructor
     *
     * @param jobQueue job queue
     * @date 2022/5/8~23:01
     */
    public ThreadManager(MyMonitor jobQueue) {
        this.jobQueue = jobQueue;
        this.pool = new MyThreadPool(40, jobQueue);
    }


    @Override
    public void run() {
        // start the thread pool
        pool.startPool();

        // set a timer
        Timer timer = new Timer();

        // set the task for above timer
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // once pool stops, timer should be terminated
                if (pool.getStopSignal())
                    timer.cancel();
                // poll the status of current job queue and thread pool and adjust # of threads in the pool
                poll();
            }
        };

        // perform a timer task
        timer.schedule(task, 100, 2000);


        // stuck until stop signal arrives
        while (!pool.getStopSignal()) ;

        pool.stopPool();
    }

    /**
     * server.process.ThreadManager.poll():
     * poll the status of job queue and thread pool and adjust the pool size
     *
     * @param
     * @return void
     * @date 2022/5/10~9:52
     */
    public void poll() {
        if (jobQueue.getQueueSize() <= 10) {
            poolDecision(5);
        } else if (jobQueue.getQueueSize() <= 20) {
            poolDecision(10);
        } else {
            poolDecision(20);
        }
    }

    /**
     * server.process.ThreadManager.poolDecision():
     * increase or decrease thread pool
     *
     * @param targetThreadNum
     * @return void
     * @date 2022/5/10~9:54
     */

    public void poolDecision(int targetThreadNum) {

        // stop at the point "equal"
        while (targetThreadNum < pool.numberThreadsRunning())
            pool.decreaseThreadsInPool();

        // if it goes through the last while loop, it will skip this while loop
        while (targetThreadNum > pool.numberThreadsRunning())
            pool.increaseThreadsInPool();
    }

}
