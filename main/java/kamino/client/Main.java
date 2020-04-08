package main.java.kamino.client;

import java.io.IOException;

public class Main {

    private static KaminoClient kaminoClient;

    public static void main(String[] args) throws IOException {

        kaminoClient = new KaminoClient();
        kaminoClient.startGUI();
    }
}
