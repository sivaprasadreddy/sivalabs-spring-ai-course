package com.sivalabs.mcpclient;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownHelper {
    private static final Parser parser = Parser.builder().build();
    private static final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public static String toHTML(String markdownText) {
        Node document = parser.parse(markdownText);
        return renderer.render(document);
    }
}