package by.gpisarev.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

public class History {

    private final LinkedList<String> history = new LinkedList<>();

    public void addMessageToHistory(String message) {
        history.add(message);
    }

    public void printStory(BufferedWriter out) throws IOException {
        if (history.size() > 0) {
            out.write("История сообщений:\n");
            for (String message : history) {
                out.write(message + "\n");
            }
            out.flush();
        }

    }
}
