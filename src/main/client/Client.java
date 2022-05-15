package main.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description
 * @create 2022-05-14
 * 
 * Constructs the client by laying out the GUI and registering a
 * listener with the textfield so that pressing Enter in the
 * listener sends the textfield contents to the server.
 */
public class Client extends Thread {
    protected final String id;
    Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;


    public Client(String id) {
        this.id = id;
    }

    public void run() {
        try {
            connectToServer();
            String[] commands = {"ADD,4,5", "MUL,6,8", "ADD,455,4355", "MUL,6346,8", "DIV,34,0", "jfdls"};
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
