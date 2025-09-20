package com.dsantos.renderer;

import com.dsantos.Template;

import java.util.Map;

public class CsvRenderer implements TemplateRenderer {
    @Override
    public String render(Template template) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Property,Value\n");

        for (Map.Entry<String, Object> entry : template.data().entrySet()) {
            csvBuilder.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append("\n");
        }

        return csvBuilder.toString();
    }

    @Override
    public String getFileExtension() {
        return "csv";
    }
}
