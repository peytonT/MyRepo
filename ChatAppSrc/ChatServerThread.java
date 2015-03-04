package ChatApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peytonT
 */


public class ChatServerThread extends Thread {

    private ChatServer server;
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ChatServerThread(ChatServer server, Socket connection) {
        this.server = server;
        this.connection = connection;
        start();
    }

    @Override
    public void run() {
        try {

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            server.outputStreamList.add(output);

            input = new ObjectInputStream(connection.getInputStream());
            while (true) {
                String msg = "";

                try {
                    msg = (String) input.readObject();
                    server.displayMessage("\n" + msg);
                    dispatchMsg(msg);
                } catch (ClassNotFoundException ex) {
                     Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    
                }

            }
        } catch (IOException ex) {
            server.displayMessage("\n" + connection + " disconnected");
        }
    }

    // send message to all connected clients except the client who sent the meassage
    public void dispatchMsg(String msg) {

        for (int i = 0; i < server.outputStreamList.size(); i++) {
            try {
                // don't send the msg back to user who sent it
                if (server.outputStreamList.get(i) != output) {
                    
                    server.outputStreamList.get(i).writeObject(msg);
                    server.outputStreamList.get(i).flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
