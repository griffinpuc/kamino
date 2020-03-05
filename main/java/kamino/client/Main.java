package main.java.kamino.client;

import java.io.IOException;

public class Main {

    private static KaminoClient kaminoClient;

    public static void main(String[] args) throws IOException {

        kaminoClient = new KaminoClient();
        kaminoClient.loadConfig();

        /*File a = new File("C:/Users/griff/OneDrive/Documents/Tangen/TangenDataPortal/databaseSlave/databaseSlave/Program.cs");
        java.awt.Desktop.getDesktop().edit(a);*/
    }
}
