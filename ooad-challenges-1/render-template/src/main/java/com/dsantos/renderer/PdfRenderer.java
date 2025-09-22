package com.dsantos.renderer;

import com.dsantos.Template;

public class PdfRenderer implements TemplateRenderer{
    @Override
    public String render(Template template) {
        StringBuilder pdf = new StringBuilder();
        pdf.append("PDF Report: ").append(template.name()).append("\n\n");
        for (var entry : template.data().entrySet()) {
            pdf.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return pdf.toString();
    }

    @Override
    public String getFileExtension() {
        return "pdf";
    }
}
