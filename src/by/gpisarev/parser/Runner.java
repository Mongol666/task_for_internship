package by.gpisarev.parser;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        String expressionText = "3 + 2 * 2 - 5 * (3 + 2)";
        List<Element> elements = Element.elementAnalyze(expressionText);
        ElementBuffer elementBuffer = new ElementBuffer(elements);
        System.out.println(Element.expr(elementBuffer));
    }
}
