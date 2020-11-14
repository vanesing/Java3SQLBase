package server;

import server.service.ServerImpl;

import java.io.IOException;
import java.sql.SQLException;

public class ChatAPP {
    public static void main(String[] args) throws SQLException, IOException {
        new ServerImpl();

    }
}