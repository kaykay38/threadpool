package main.client;



import main.server.process.JobQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description
 * @create 2022-05-11 17:35
 */
public class TestClient2 {
    public static void main(String[] args) throws Exception {
        Client1[] clients = new Client1[50];
        // Thread.sleep(5000L);
        for (int x = 0; x < clients.length; x++) {

            clients[x] = new Client1(x);
            clients[x].start();
        }
        for (int x = 0; x < clients.length; x++) {
            clients[x].join();
            System.out.println("client" + x + " finished");
        }
        System.out.println("Parallel Tests are done!!");


    }

    public static class Client1 extends Thread {
        private final int id;
        private BufferedReader in;
        private PrintWriter out;
        Socket socket;
        String[] commands = {"ADD,4,5", "MUL,6,8", "ADD,455,4355", "MUL,6346,8"};

        public Client1(int id) {
            this.id = id;
        }
        public void run() {
            String response;

            String cmd;
            try {
                connectToServer();

                for (int i = 0; i < 4; i++) {

                    cmd = commands[i % 4];
                    System.out.println("client" + id + ": cmd" + i + "| " + cmd);
                    out.println(cmd);

                    try {

                        response = in.readLine();

                        if (response == null || response.equals("")) {
                            System.out.println("client to terminate.");
                        }

                    } catch (IOException ex) {
                        response = "Error: " + ex;
                        System.out.println("" + response + "\n");
                    }
                    System.out.println("Hello client " + id + ", here is result: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void connectToServer() throws IOException {

            // Get the server address from a dialog box.
            String serverAddress = "localhost";

            // Make connection and initialize streams
            socket = new Socket(serverAddress, 9898);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Consume the initial welcoming messages from the server
        }

    }
}
