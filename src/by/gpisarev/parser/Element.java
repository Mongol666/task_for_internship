package by.gpisarev.parser;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private TypeOfElement type;
    private String value;

    public Element(TypeOfElement type, String value) {
        this.type = type;
        this.value = value;
    }

    public Element(TypeOfElement type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

    @Override
    public String toString() {
        return "Element{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    public static List<Element> elementAnalyze(String expression) {
        List<Element> elements = new ArrayList<>();
        int position = 0;
        while (position < expression.length()) {
            char c = expression.charAt(position);
            switch (c) {
                case '(':
                    elements.add(new Element(TypeOfElement.LEFT_BRACKET, c));
                    position++;
                    continue;
                case ')':
                    elements.add(new Element(TypeOfElement.RIGHT_BRACKET, c));
                    position++;
                    continue;
                case '+':
                    elements.add(new Element(TypeOfElement.PLUS, c));
                    position++;
                    continue;
                case '-':
                    elements.add(new Element(TypeOfElement.MINUS, c));
                    position++;
                    continue;
                case '*':
                    elements.add(new Element(TypeOfElement.MULTIPLY, c));
                    position++;
                    continue;
                case '/':
                    elements.add(new Element(TypeOfElement.DIV, c));
                    position++;
                    continue;
                default:
                    if (c >= '0' && c <= '9') {
                        StringBuilder builder = new StringBuilder();
                        do {
                            builder.append(c);
                            position++;
                            if (position >= expression.length()) {
                                break;
                            }
                            c = expression.charAt(position);
                        } while (c >= '0' && c <= '9');
                        elements.add(new Element(TypeOfElement.NUMBER, builder.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Неправильное выражение: " + expression);
                        }
                        position++;
                    }
            }
        }
        elements.add(new Element(TypeOfElement.EOF, ""));
        return elements;
    }

    public static int expr(ElementBuffer elements) {
        Element element = elements.next();
        if (element.type == TypeOfElement.EOF) {
            return 0;
        } else {
            elements.back();
            return plusminus(elements);
        }
    }

    public static int factor(ElementBuffer elements) {
        Element element = elements.next();
        switch (element.type) {
            case NUMBER:
                return Integer.parseInt(element.value);
            case LEFT_BRACKET:
                int value = expr(elements);
                element = elements.next();
                if (element.type != TypeOfElement.RIGHT_BRACKET) {
                    throw new RuntimeException("Неправильное выражение: " + elements);
                }
                return value;
            default:
                throw new RuntimeException("Неправильное выражение: " + elements);
        }
    }

    public static int plusminus(ElementBuffer elements) {
        int value = multdiv(elements);
        while (true) {
            Element element = elements.next();
            switch (element.type) {
                case PLUS:
                    value += multdiv(elements);
                    break;
                case MINUS:
                    value -= multdiv(elements);
                    break;
                default:
                    elements.back();
                    return value;
            }
        }
    }

    public static int multdiv(ElementBuffer elements) {
        int value = factor(elements);
        while (true) {
            Element element = elements.next();
            switch (element.type) {
                case MULTIPLY:
                    value *= factor(elements);
                    break;
                case DIV:
                    value /= factor(elements);
                    break;
                default:
                    elements.back();
                    return value;
            }
        }
    }


}
