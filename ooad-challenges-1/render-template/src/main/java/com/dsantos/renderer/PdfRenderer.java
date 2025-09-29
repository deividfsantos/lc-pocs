package com.dsantos.renderer;

import com.dsantos.Template;

import java.util.Map;

public class PdfRenderer implements TemplateRenderer {
    
    @Override
    public String render(Template template) {
        StringBuilder pdf = new StringBuilder();
        
        pdf.append("%PDF-1.4\n");
        pdf.append("1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");
        pdf.append("2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");
        pdf.append("3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] ");
        pdf.append("/Resources << /Font << /F1 << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> >> >> ");
        pdf.append("/Contents 4 0 R >>\nendobj\n");
    
        StringBuilder content = new StringBuilder();
        content.append("BT /F1 12 Tf 50 750 Td (").append(template.name()).append(") Tj ");
        for (Map.Entry<String, Object> entry : template.data().entrySet()) {
            content.append("0 -15 Td (").append(entry.getKey()).append(": ").append(entry.getValue()).append(") Tj ");
        }
        content.append("ET");
        
        pdf.append("4 0 obj\n<< /Length ").append(content.length()).append(" >>\nstream\n");
        pdf.append(content).append("\nendstream\nendobj\n");
        pdf.append("xref\n0 5\ntrailer\n<< /Size 5 /Root 1 0 R >>\nstartxref\n%%EOF");
        
        return pdf.toString();
    }

    @Override
    public String getFileExtension() {
        return "pdf";
    }
}
