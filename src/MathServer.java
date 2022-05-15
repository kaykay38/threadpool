import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description
 * @create 2022-05-11 17:36
 */
public class MathServer {
    public static void main(String[] args) throws IOException {
        System.out.println("The Math Server is running.");
        ServerSocket listener = new ServerSocket(9898, 500);
        JobQueue jobQueue = new JobQueue(50);
        Acceptor acceptor = new Acceptor(listener, jobQueue);
        ThreadManager manager = new ThreadManager(jobQueue);
        Handler handler = new Handler(acceptor.getSocketQueue(), jobQueue);

        try {
            acceptor.start();
            manager.start();
            handler.start();
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
