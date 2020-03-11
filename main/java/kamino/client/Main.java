package main.java.kamino.client;

import main.java.kamino.library.KaminoClient;

import java.io.IOException;

public class Main {

    private static KaminoClient kaminoClient;

    public static void main(String[] args) throws IOException {

        kaminoClient = new KaminoClient();
        //kaminoClient.startMainGUI();
        kaminoClient.startGUI();
        //kaminoClient.connectClient();

    }
}
