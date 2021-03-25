package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.logging.LogManager;

public class StartServer {
    public static void main(String[] args) {
        try {
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration(new FileInputStream("logging.properties"));
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
