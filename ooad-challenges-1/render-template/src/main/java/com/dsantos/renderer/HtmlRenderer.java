package com.dsantos.renderer;

import com.dsantos.Template;

public class HtmlRenderer implements TemplateRenderer {
    @Override
    public String render(Template template) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>")
            .append(template.name())
            .append("</title></head><body>");
        return html.toString();
    }

    @Override
    public String getFileExtension() {
        return "html";
    }
}
