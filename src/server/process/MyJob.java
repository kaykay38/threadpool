package server.process;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-08 18:33
 */
public class MyJob {
    private String clientInfo;
    private String instruction;



    /**
     * server.process.MyJob.MyJob():
     * constructor
     * @date 2022/5/8~18:33
     * @param clientInfo from which client
     * @param instruc instructions to be performed
     * @return
     */
    public MyJob(String clientInfo, String instruc) {
        this.clientInfo = clientInfo;
        this.instruction = instruc;
    }

    /**
     * server.process.MyJob.getInstruc():
     * get the instruction
     * @date 2022/5/8~21:32
     * @param
     * @return java.lang.String
     */

    public String getInstruc() {
        return instruction;
    }

    /**
     * server.process.MyJob.getClientInfo():
     * get the client info
     * @date 2022/5/8~18:36
     * @param
     * @return java.lang.String
     */
    public String getClientInfo() {
        return clientInfo;
    }
}
