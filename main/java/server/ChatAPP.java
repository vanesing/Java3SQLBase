package server;

import server.service.ServerImpl;

import java.sql.SQLException;

public class ChatAPP {
    public static void main(String[] args) throws SQLException {
        new ServerImpl();

    }
}