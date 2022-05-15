package main.client;
import java.io.IOException;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description
 * @create 2022-05-11 17:35
 */
public class MathClientCLITest {

    public static void main(String[] args) throws Exception {
        Client[] clients = new Client[20];
        for (int i = 0; i < clients.length - 1; i++) {
            clients[i] = new Client("client"+i);
            clients[i].start();
        }
        for (int i = 0; i < clients.length - 1; i++) {
            clients[i].join();
            System.out.println("client" + i + " finished");
        }
        System.out.println();

        KillClient killClient = new KillClient("main");
        killClient.start();
        killClient.join();
        System.out.println("Parallel Tests are done!!");
    }

    public static class KillClient extends Client {
        public KillClient(String id) {
            super(id);
        }

        @Override
        public void run() {
            try {
                super.connectToServer();
                super.sendInstructionReceiveResponse(super.id + "|cmd| KILL");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}