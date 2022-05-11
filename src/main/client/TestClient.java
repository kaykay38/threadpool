package main.client;

import server.io.Acceptor;
import server.io.Handler;
import server.process.MyMonitor;
import server.process.ThreadManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Tianyang Liao
 * @course CSCD 467
 * @Description client file for test use
 * @create 2022-05-10 16:56
 */
public class TestClient {
    public static void main(String[] args) throws Exception {
        // Get the server address from a dialog box.
        String serverAddress = "localhost";

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(1);

        // send instruction to server
//        String testInstruction = "ADD,2,3";
//        out.println(testInstruction);
        System.out.println(2);

        // get the response
        System.out.println(3);
        for (int i = 0; i < 1000000; i++) {
            String response = in.readLine();
            System.out.println(response);

        }

    }


}
