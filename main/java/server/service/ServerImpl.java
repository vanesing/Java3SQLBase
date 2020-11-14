package server.service;

import server.handler.ClientHandler;
import server.inter.AuthService;
import server.inter.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

public class ServerImpl implements Server {

    private List<ClientHandler> clients;
    private AuthService authService;
    private static final Logger logger = Logger.getLogger(ServerImpl.class.getName());

    public ServerImpl() throws SQLException, IOException {
        logger.setLevel(Level.ALL);
        try {
            ServerSocket serverSocket = new ServerSocket(PORT); // Создаем сокет на сервере
            authService = new AuthServiceImpl(); // Создаем список авторизованных клиентов
            authService.start(); // Сообщение о запуске службы авторизации клиентов
            clients = new LinkedList<>(); // Создаем список клиентов
            // Цикл подключения клиентов
            while (true) { // Подключение клиентов
                Handler handler = new FileHandler("LOG.log");
                handler.setFormatter(new SimpleFormatter());
                logger.log(Level.SEVERE,"Ожидаем подключения клиентов");
                logger.addHandler(handler);
                System.out.println("Ожидаем подключения клиентов");
                Socket socket = serverSocket.accept(); // Ожидание подключения клиента
                logger.log(Level.SEVERE,"Клиент подключился");
                logger.addHandler(handler);
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket); // Создаем для каждого клиент свой обработчик
            }
        } catch ( IOException | SQLException | ClassNotFoundException e) {
            Handler handlerWarn = new FileHandler("Warn.log");
            handlerWarn.setFormatter(new SimpleFormatter());
            logger.log(Level.WARNING,"Проблема на сервере");
            logger.addHandler(handlerWarn);
            System.out.println("Проблема на сервере");
        } finally {
            if (authService != null) {
                authService.stop(); // Сообщение об остановке сервера аутентификации
                Handler handlerStop = new FileHandler("LogStop.log");
                handlerStop.setFormatter(new SimpleFormatter());
                logger.log(Level.WARNING,"Сервер остановлен");
                logger.addHandler(handlerStop);
            }
        }
    }

    // Метод отсылки приватного сообщения
    public void sendPrivateMsg(ClientHandler fromClient, String toClient, String msg) throws IOException {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getNick().equals(toClient)) {
                clientHandler.sendMsg("От " + fromClient.getNick() + ": " + msg);
                fromClient.sendMsg("Кому " + toClient + ": " + msg);
                return;
            } else {
                fromClient.sendMsg(toClient + " не подключен к чату!");
                Handler handlerConnCl = new FileHandler("Ошибки подключения к чату.log");
                handlerConnCl.setFormatter(new SimpleFormatter());
                logger.log(Level.SEVERE,"Не подключен к чату");
                logger.addHandler(handlerConnCl);
            }
        }
    }

    // Метод проверки клиента на задвоение
    @Override
    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler c : clients) {
            if (c.getNick() != null && c.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    // Метод рассылки сообщений списку
    @Override
    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    // Метод добавления клиента в список рассылки сообщений
    @Override
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    // Метод удаления клиента из рассылки
    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    // Метод возвращающий список авторизованных пользователей
    @Override
    public AuthService getAuthService() {
        return authService;
    }

    @Override
    public synchronized void sendMsgToClient(ClientHandler from, String to, String msg) {
        for (ClientHandler c : clients) {
            if (c.getNick().equals(to)) {
                c.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("client " + to + ": " + msg);
                return;
            }
        }
    }

    @Override
    public synchronized void broadcastClientList() {
        StringBuilder builder = new StringBuilder("/clients");
        for (ClientHandler c : clients) {
            builder.append(c.getNick() + " ");
        }
        broadcastMsg(builder.toString());
    }

}
