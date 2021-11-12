package by.gpisarev.socket;

public class Client {

    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 8080;


    public static void main(String[] args) {
        new ClientUtilities(IP_ADDRESS, PORT);
    }
}