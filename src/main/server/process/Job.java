package main.server.process;
import java.net.Socket;

import main.server.util.TimeUtil;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 18:33
 */
public class Job {
    private final Socket clientSocket;
    private final String clientId;
    private final String instructionId;
    private final String instruction;
    private final String timeStamp;

    /**
     * server.process.MyJob.MyJob():
     * constructor
     * @date 2022/5/8~18:33
     * @param clientSocket from which client
     * @param instruction instructions to be performed
     */
    public Job(Socket clientSocket, String instruction, String instructionId, String clientId) {
        this.clientSocket = clientSocket;
        this.instruction = instruction;
        this.instructionId = instructionId;
        this.clientId = clientId;
        this.timeStamp = TimeUtil.getTimeStamp();
    }

    /**
     * server.process.MyJob.getClientSocket():
     * get the client info
     * @date 2022/5/8~18:36
     * @return Socket
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * server.process.MyJob.getInstruction():
     * get the instructionId():
     * get the instructionId
     * @date 2022/5/8~21:32
     * @return int
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * server.process.MyJob.getInstruction():
     * get the instructionId():
     * get the instructionId
     * @date 2022/5/8~21:32
     * @return int
     */
    public String getInstructionId() {
        return instructionId;
    }

    /**
     * server.process.MyJob.getInstruction
     * @date 2022/5/8~21:32
     * @return java.lang.String
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * server.process.MyJob.getTimeStamp():
     * get the time stamp
     * @date 2022/5/8~21:32
     * @return java.lang.String
     */
    public String getTimeStamp() {
        return timeStamp;
    }
}