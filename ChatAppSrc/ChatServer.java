package ChatApp;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *  
 *
 * @author peytonT
 */
public class ChatServer extends JFrame {

    private final JTextField chatBox;

    private final JTextArea chatWindow;
    private ObjectOutputStream output;

    private ServerSocket server;

    // to store outputStream of all connected clients
    protected ArrayList<ObjectOutputStream> outputStreamList = new ArrayList<ObjectOutputStream>();

    final int SERVER_PORT = 8888;

    public ChatServer() {
        super("Chat Server");

        chatBox = new JTextField();

        // chatBox.setEditable(false);
        chatBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        chatBox.setText("");

                    }
                }
        );

        add(chatBox, BorderLayout.SOUTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(530, 350);
        setVisible(true);

    }

    public void startServer() throws IOException {

        server = new ServerSocket(SERVER_PORT);
        listen();

    }

    private void listen() throws IOException {
        displayMessage("Chat Room started. Waiting for connections...\n");
        while (true) {
            Socket connection = server.accept();

            sendMessage(connection + " has joined\n");
            
            // start a thread to handle this connection
            new ChatServerThread(this, connection);

        }
    }

    // send mesage to connected client
    private void sendMessage(String message) {

        try {

            for (int i = 0; i < outputStreamList.size(); i++) {

                outputStreamList.get(i).writeObject("SERVER: " + message);
                outputStreamList.get(i).flush();

            }

            displayMessage("\nSERVER: " + message);
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    // update chat window
    void displayMessage(final String msg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append(msg);
                    }
                }
        );

    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            server.startServer();
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
