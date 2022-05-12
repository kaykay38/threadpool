package main.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
        System.out.println("The test client is running.");

        // send instruction to server
//        System.out.println("Send instructions to server:");
//        Scanner scanner = new Scanner(System.in);
        String testInstruction;
//        while (!socket.isInputShutdown() && (testInstruction = scanner.nextLine()) != null) {
//            out.println(testInstruction);
//            System.out.println("Server response: " + in.readLine());
//        }
        int i = 1;
        testInstruction = "ADD,3,4";
        System.out.println("Instruction: " + testInstruction);
        while (!socket.isInputShutdown() && i <= 1000000) {
//            System.out.println(i + " Instruction: " + testInstruction);
            out.println(testInstruction);
            if (i == 1000000) {
                System.out.println("Response: " + in.readLine());
                System.out.println(i + " instructions sent.");
            }
            i++;
        }
        out.println("KILL");
    }


}