package server.service;

import server.inter.AuthService;
import server.inter.Test;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

public class AuthServiceImpl implements AuthService {
    private List<UserEntity> usersList;
    public static Connection conn;
    public static Statement statment ;
    public static ResultSet resultSet;

    public AuthServiceImpl() throws SQLException, ClassNotFoundException {
        /*this.usersList = new LinkedList<>();
        this.usersList.add(new UserEntity("login1", "pass1", "nick1"));
        this.usersList.add(new UserEntity("login2", "pass2", "nick2"));
        this.usersList.add(new UserEntity("login3", "pass3", "nick3"));

         */



    }

    @Override
    public void start() throws SQLException, ClassNotFoundException {

        setConn();
        createDB();
        writeDB();
        System.out.println("Сервис аутентификации запущен");
    }


    @Override
    public String getNick(String login, String password) throws SQLException {
        readDB();
        return null;
    }

    @Override
    public void stop() throws SQLException {

        closeDB();
        System.out.println("Сервис аутентификации остановлен");

    }


    public void setConn() throws SQLException, ClassNotFoundException {
        Class.forName("org.mysql.jdbc");
        conn =DriverManager.getConnection("jdbc:mysql:test.db");

    }


    public void createDB() throws SQLException  {
        statment = conn.createStatement();
        statment.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'nick' text, 'password' text);");

    }


    public void writeDB() throws SQLException {
        statment.execute("INSERT INTO 'users' ('login','nick','password') VALUES ('login1', 'master','123456');");
        statment.execute("INSERT INTO 'users' ('login','nick','password') VALUES ('login2', 'master1','129456');");
        statment.execute("INSERT INTO 'users' ('login','nick','password') VALUES ('login3', 'master2','123486');");
    }

    public void readDB() throws SQLException {
        resultSet = statment.executeQuery("SELECT * FROM  user");

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String login = resultSet.getNString("login");
            String nick = resultSet.getNString("nick");
            String password = resultSet.getNString("password");

            System.out.println(id + " " +  login + " " + nick + " " +password);
        }
    }


    public void closeDB() throws SQLException  {
        resultSet.close();
        statment.close();
        conn.close();
        ///


    }


    private class UserEntity {
        private String login;
        private String password;
        private String nick;

        public UserEntity(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }
}
