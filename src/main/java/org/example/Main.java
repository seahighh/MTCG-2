package org.example;


import org.example.application.game.MTCGgame;
import org.example.application.game.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new MTCGgame());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}