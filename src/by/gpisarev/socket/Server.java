package by.gpisarev.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<ServerUtilities> serverUtilitiesList = new LinkedList<>();
    public static History history;


    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            history = new History();
            System.out.println("Сервер запущен!");
            while (true) {
                Socket socket = server.accept();
                if (!serverUtilitiesList.add(new ServerUtilities(socket))) {
                    break;
                }
            }
        }
    }
}
