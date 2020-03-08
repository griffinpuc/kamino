package main.java.kamino.server;

import javax.sql.DataSource;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientConnection extends Thread {

    protected Socket socket;
    private DatabaseConnection connectionContext;

    public ClientConnection(Socket socket, DataSource dataSource) {
        try{
            this.socket = socket;
            this.connectionContext = new DatabaseConnection(dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}
