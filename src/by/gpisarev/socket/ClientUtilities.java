package by.gpisarev.socket;

import java.io.*;
import java.net.Socket;


public class ClientUtilities {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inFromConsole;
    private String login;

    public ClientUtilities(String address, int port) {
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            System.err.println("Соединение не установлено...");
        }

        try {
            inFromConsole = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            pressNickname();
            new ReadingThread().start();
            new WritingMessage().start();
        } catch (IOException e) {
            try {
                downService();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void pressNickname() throws IOException {
        System.out.print("Введите свой логин:  ");
        login = inFromConsole.readLine();
        out.write("Привет, " + login + "\n");
        out.flush();
    }

    private void downService() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
            in.close();
            out.close();
        }
    }

    private class ReadingThread extends Thread {
        @Override
        public void run() {
            String message;
            try {
                while (true) {
                    message = in.readLine();
                    if (message.equals("exit")) {
                        downService();
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                try {
                    downService();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class WritingMessage extends Thread {
        @Override
        public void run() {
            while (true) {
                String message;
                try {
                    message = inFromConsole.readLine();
                    if (message.equals("exit")) {
                        out.write("exit\n");
                        downService();
                        break;
                    } else {
                        out.write(login + ": " + message + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    try {
                        downService();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }
    }
}
