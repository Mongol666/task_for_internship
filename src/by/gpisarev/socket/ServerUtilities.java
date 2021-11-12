package by.gpisarev.socket;

import java.io.*;
import java.net.*;


class ServerUtilities extends Thread {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ServerUtilities(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Server.history.printStory(out);
        start();
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                out.write(message + "\n");
                out.flush();

                message = in.readLine();
                if (message.equals("exit")) {
                    downService();
                    break;
                }
                System.out.println("Сообщение: " + message);
                Server.history.addMessageToHistory(message);
                for (ServerUtilities serverSomething : Server.serverUtilitiesList) {
                    serverSomething.sendMessageForAll(message);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void sendMessageForAll(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    private void downService() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
            in.close();
            out.close();
            for (ServerUtilities serverUtility : Server.serverUtilitiesList) {
                if (serverUtility.equals(this)) {
                    Server.serverUtilitiesList.remove(this);
                    serverUtility.interrupt();
                }
            }
        }
    }
}

