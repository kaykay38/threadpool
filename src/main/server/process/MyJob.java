package main.server.process;

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
     */
    public MyJob(Socket clientSocket, String instruction) {
        this.clientSocket = clientSocket;
        this.instruction = instruction;
    }

    /**
     * server.process.MyJob.getClientSocket():
     * get the client info
     * @date 2022/5/8~18:36
     * @return java.lang.String
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * server.process.MyJob.getInstruction():
     * set the client info
     * @param clientSocket - Socket
     */
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * server.process.MyJob.getInstruction():
     * get the instruction
     * @date 2022/5/8~21:32
     * @return java.lang.String
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * server.process.MyJob.getClientSocket():
     * set the instruction
     * @param instruction - String
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

}
