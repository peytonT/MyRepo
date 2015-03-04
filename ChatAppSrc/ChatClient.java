package ChatApp;

/**
 *
 * @author peytonT
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class ChatClient extends JFrame {

    private JTextField chatBox;
    private final JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private final String serverIP;
    private Socket connectionToServer;
    private static String username = "";

	// change server IP here
    final static String SERVER_IP = "192.168.1.100";

    public ChatClient(String chatServer) throws UnknownHostException {
        super(username);

        serverIP = chatServer;
        chatBox = new JTextField();

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
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(530, 350);
        setVisible(true);
    }

    public void start() throws IOException {
        try {
            connectToServer();
            chatting();

        } finally {
            close();
        }
    }

    private void connectToServer() {
        try {
            displayMessage("Connecting to server... \n");
            connectionToServer = new Socket(InetAddress.getByName(serverIP), 8888);
            displayMessage("Connected to: " + connectionToServer.getInetAddress().getHostAddress()+ "\n");
        } catch (IOException ex) {
            displayMessage("Cannot connect to SERVER");
        }

    }
    
    // create input/ouput streams to recieve and send message
    private void chatting() {
        try {
            output = new ObjectOutputStream(connectionToServer.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connectionToServer.getInputStream());
            while (true) {

                try {
                    message = (String) input.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                displayMessage("\n" + message);

            }
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void close() {

        try {
            output.close();
            input.close();
            connectionToServer.close();
        } catch (IOException ioException) {
        }
    }

    //send messages to server
    private void sendMessage(String msg) {

        try {
            output.writeObject(username + ": " + msg);
            output.flush();
            displayMessage("\n" + username + ": " + msg);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // update chatwindow
    private void displayMessage(final String m) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(m);
                    }
                }
        );
    }

    public static void main(String[] args) throws UnknownHostException {
        username = JOptionPane.showInputDialog(null, null, "Enter your name", JOptionPane.QUESTION_MESSAGE);

        ChatClient client;
        client = new ChatClient(SERVER_IP);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
