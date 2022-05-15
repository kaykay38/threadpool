import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public static class Client extends Thread {
        private final String id;
        Socket socket;
        private BufferedReader in;
        private PrintWriter out;


        public Client(String id) {
            this.id = id;
        }

        public void run() {
            try {
                connectToServer();
                String[] commands = {"ADD,4,5", "MUL,6,8", "ADD,455,4355", "MUL,6346,8"};
                String currentCommand;
                for (int i = 0; i < 100; i++) {
                    currentCommand = commands[i % 4];
                    sendInstructionReceiveResponse(id + "|cmd" + i + "| " + currentCommand);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void sendInstructionReceiveResponse(String instruction) {
            System.out.println(instruction);
            out.println(instruction);
            String response = "";
            try {
                response = in.readLine();
                if (response == null || response.equals("")) {
                    System.out.println("client to terminate.");
                }
            } catch (IOException ex) {
                response = "Error: " + ex;
                System.out.println(response);
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println(response);
            System.out.println();
        }

        public void connectToServer() throws IOException {
            // Get the server address from a dialog box.
            String serverAddress = "localhost";

            // Make connection and initialize streams
            socket = new Socket(serverAddress, 9898);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }
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