package com.dsantos.renderer;

import com.dsantos.Template;

public class HtmlRenderer implements TemplateRenderer {

    @Override
    public String render(Template template) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <title>").append(template.getName()).append("</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <h1>").append(template.getName()).append("</h1>\n");
        html.append("    <table border='1'>\n");
        
        for (Map.Entry<String, Object> entry : template.getData().entrySet()) {
            html.append("        <tr><td>").append(entry.getKey()).append("</td>")
                .append("<td>").append(entry.getValue()).append("</td></tr>\n");
        }
        
        html.append("    </table>\n");
        html.append("</body>\n");
        html.append("</html>");
        return html.toString();
    }

    @Override
    public String getFileExtension() {
        return "html";
    }
}
