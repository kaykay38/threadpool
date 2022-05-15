package main.server.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import main.server.util.JobQueue;
import main.server.util.SocketQueue;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description handle reading requests from all sockets
 * @create 2022-05-09 23:57
 */
public class Handler extends Thread{
    private SocketQueue socketQueue;
    private JobQueue jobQueue;

    /**
     * server.io.Handler.Handler():
     * constructor
     * @date 2022/5/10~10:20
     * @param socketQueue the socketQueue grabbed from Acceptor
     * @return
     */
    public Handler(SocketQueue socketQueue, JobQueue jobQueue) {
        this.socketQueue = socketQueue; // ATTENTION!!!!!NOT THREAD SAFE!!!
        this.jobQueue = jobQueue;
    }

    /**
     * server.io.Handler.doRead():
     * scan through input streams of all sockets in the socket queue <br>
     * and enqueue all tasks into the job queue (only 1 time)
     * @date 2022/5/10~11:22
     * @param
     * @return void
     */
    public void doRead() {
        BufferedReader in;
        String input = null;
        String[] inputArr;
        for (Socket socket: socketQueue) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                if (in.ready())
                    input = in.readLine();
                if (input != null) {
                    inputArr = input.split("\\s*\\|\\s*");
                    if(inputArr.length == 3) {
                        jobQueue.enqueue(socket, inputArr[2], inputArr[1], inputArr[0]);
                    } else {
                        jobQueue.enqueue(socket, input, "", "");
                    }
                }
            } catch (SocketException e) {
                socketQueue.dequeue();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            doRead();
        }
    }
}
