package com.alisonadamus.astronix.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {
    public String renderHtml(String markdown) {
        if (markdown == null) return "";
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .escapeHtml(true)
                .build();
        return renderer.render(document);
    }
}