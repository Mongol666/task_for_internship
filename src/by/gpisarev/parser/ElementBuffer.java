package by.gpisarev.parser;

import java.util.List;

public class ElementBuffer {
    private int position;

    public List<Element> elements;

    public ElementBuffer(List<Element> elements) {
        this.elements = elements;
    }

    public Element next() {
        return elements.get(position++);
    }

    public void back() {
        position--;
    }

    public int getPosition() {
        return position;
    }
}
