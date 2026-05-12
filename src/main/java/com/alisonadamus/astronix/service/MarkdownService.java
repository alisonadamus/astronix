package com.alisonadamus.astronix.service;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MarkdownService {

    public String renderHtml(String markdown) {
        if (markdown == null) return "";
        List<org.commonmark.Extension> extensions = Collections.singletonList(TablesExtension.create());

        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .escapeHtml(false)
                .build();

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}