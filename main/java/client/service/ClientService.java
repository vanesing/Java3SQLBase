package client.service;

import server.inter.LogCreater;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientService extends JFrame implements LogCreater {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String myNick;

    public ClientService() {
    }

    private void start() {
        myNick = "";

        try {
            socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            setAutorized(false);
            Thread t1 = new Thread(() -> {
                try {
                    while (true) {
                        String strMsg = dis.readUTF();
                        if (strMsg.startsWith("/authOk")) {
                            setAutorized(true);
                            myNick = strMsg.split("\\s")[1];
                            //Пишет в лог файл
                            LogIn();
                            break;
                        }
                        //chatArea.appendText(strMsg + "\n");
                    }
                    while (true) {
                        String strMsg = dis.readUTF();
                        if (strMsg.equals("/exit")) {
                            break;
                        }
                        //chatArea.appendText(strMsg + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        setAutorized(false);
                        socket.close();
                        myNick = "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.setDaemon(true);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void omAuthClick () {
        if (socket == null || socket.isClosed()) {
            start();
        }
        try {
            dos.writeUTF("/auth" );
            //Вывод из лога последних 100 строк
            Logout();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setAutorized(boolean b) {
    }

    @Override
    public void Logout() throws IOException {
        //Вывод из лога ну не последних 100 строк, всего лога...))


        dis.readUTF((DataInput) new BufferedInputStream(new FileInputStream("Log.txt")));
        dis.close();


    }

    @Override
    public void LogIn() throws IOException {
        //Пишет в лог файл
        dos.writeUTF(String.valueOf(new BufferedOutputStream(new FileOutputStream("Log.txt"))));


    }
}
