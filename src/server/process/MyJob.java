package server.process;

import java.net.Socket;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 18:33
 */
public class MyJob {
    private Socket clientSocket;
    private String instruction;



    /**
     * server.process.MyJob.MyJob():
     * constructor
     * @date 2022/5/8~18:33
     * @param clientSocket from which client
     * @param instruction instructions to be performed
     * @return
     */
    public MyJob(Socket clientSocket, String instruction) {
        this.clientSocket = clientSocket;
        this.instruction = instruction;
    }

    /**
     * server.process.MyJob.getInstruction():
     * get the instruction
     * @date 2022/5/8~21:32
     * @param
     * @return java.lang.String
     */

    public String getInstruction() {
        return instruction;
    }

    /**
     * server.process.MyJob.getClientSocket():
     * get the client info
     * @date 2022/5/8~18:36
     * @param
     * @return java.lang.String
     */
    public Socket getClientSocket() {
        return clientSocket;
    }
}
