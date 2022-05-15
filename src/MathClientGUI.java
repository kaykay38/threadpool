import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @Description
 * @create 2022-05-11 17:35
 */
public class MathClientGUI {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
             System.out.println("Usage: java main.client.MathClientGUI <client name/ID>");
             System.out.println("Example: java main.client.MathClientGUI client1");
             System.exit(1);
        }
        GUIClient client = new GUIClient(args[0]);
        client.start();
        client.join();
    }

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public static class GUIClient extends Client {
        private JFrame frame = new JFrame("Math Client");
        private JTextField dataField = new JTextField(40);
        private JTextArea messageArea = new JTextArea(8, 60);
    
        private int cmdCount = 0;


        public GUIClient(String id) {
            super(id);
            // Layout GUI
            messageArea.setEditable(false);
            frame.getContentPane().add(dataField, "North");
            frame.getContentPane().add(new JScrollPane(messageArea), "Center");

            // Add Listeners
            dataField.addActionListener(new ActionListener() {
                /**
                 * Responds to pressing the enter key in the textfield
                 * by sending the contents of the text field to the
                 * server and displaying the response from the server
                 * in the text area.   If the response is "." we exit
                 * the whole application, which closes all sockets,
                 * streams and windows.
                 */
                public void actionPerformed(ActionEvent e) {
                    cmdCount++;
                    String cmd = dataField.getText();
                    out.println(id + "|" + "cmd" + cmdCount + "| "+ cmd);
                    String response = "";
                    try {
                        response = in.readLine();
                        if (response == null || response.equals("")) {
                            System.out.println("client to terminate.");
                            System.exit(0);
                        }
                    } catch (IOException ex) {
                        response = "Error: " + ex;
                        System.out.println(response);
                    }
                    messageArea.append(cmd + ": " + response + "\n");
                    dataField.selectAll();
                }
            });
        }

        @Override
        public void run() {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            try {
                connectToServer();
            } catch (IOException ex) {
                ex.printStackTrace();
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
        }
    }
}