import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @author Mia Hunt, Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 15:54
 */
public class ThreadManager extends Thread {
    private JobQueue jobQueue;
    private MyThreadPool pool;
    private int threshold1; // first threshold
    private int threshold2; // second threshold
    private int pollInterval; // every pollInterval * 10 milliseconds, poll the status of thread pool

    /**
     * server.process.ThreadManager.ThreadManager():
     * constructor
     * @param jobQueue job queue
     * @date 2022/5/8~23:01
     */
    public ThreadManager(JobQueue jobQueue) {
        this.jobQueue = jobQueue;
        this.pool = new MyThreadPool(40, jobQueue);
        this.threshold1 = 10;
        this.threshold2 = 20;
        this.pollInterval = 2;
    }

    public ThreadManager(JobQueue jobQueue, int threshold1, int threshold2, int pollInterval) {
        this.jobQueue = jobQueue;
        this.pool = new MyThreadPool(40, jobQueue);
        this.threshold1 = threshold1;
        this.threshold2 = threshold2;
        this.pollInterval = pollInterval;
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
                if (pool.isStopped()) {
                    timer.cancel();
                }
                // poll the status of current job queue and thread pool and adjust # of threads in the pool
                poll();
                try {
                    Thread.sleep(pollInterval * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // perform a timer task
        timer.schedule(task, 100, 2000);

        // stuck until stop signal arrives
        while (!pool.isStopped());
        pool.stopPool();
        // System.exit(0);
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
        if (jobQueue.size() <= threshold1) {
            poolDecision(5);
        } else if (jobQueue.size() <= threshold2) {
            poolDecision(10);
        } else {
            poolDecision(20);
        }
    }

    /**
     * server.process.ThreadManager.poolDecision():
     * increase or decrease thread pool
     * @param targetThreadNum
     * @return void
     * @date 2022/5/10~9:54
     */
    public void poolDecision(int targetThreadNum) {

        // stop at the point "equal"
        while (targetThreadNum < pool.getNumberThreadsRunning()) {
            pool.decreaseThreads();
        }

        // if it goes through the last while loop, it will skip this while loop
        while (targetThreadNum > pool.getNumberThreadsRunning()) {
            pool.increaseThreads();
        }
    }
}
