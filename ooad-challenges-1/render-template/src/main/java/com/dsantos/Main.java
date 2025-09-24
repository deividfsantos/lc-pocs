package com.dsantos;

import com.dsantos.template.Template;
import com.dsantos.template.TemplateEngine;
import com.dsantos.template.TemplateEngine.RenderFormat;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Template Rendering System Demo ===\n");
        
        // Create sample data for the template
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("Customer ID", "12345");
        customerData.put("Name", "João Silva");
        customerData.put("Email", "joao.silva@email.com");
        customerData.put("Phone", "+55 11 99999-9999");
        customerData.put("Address", "Rua das Flores, 123 - São Paulo, SP");
        customerData.put("Registration Date", "2025-09-18");
        customerData.put("Status", "Active");
        customerData.put("Credit Limit", "R$ 10,000.00");
        
        Template customerTemplate = new Template("Customer Report", customerData);
        
        TemplateEngine engine = new TemplateEngine();
        
        System.out.println("1. Rendering template in HTML format:");
        String htmlOutput = engine.render(customerTemplate, RenderFormat.HTML);
        System.out.println(htmlOutput);
        System.out.println("\n");
        
        System.out.println("2. Rendering template in CSV format:");
        String csvOutput = engine.render(customerTemplate, RenderFormat.CSV);
        System.out.println(csvOutput);
        System.out.println("\n");
        
        System.out.println("3. Rendering template in PDF format:");
        String pdfOutput = engine.render(customerTemplate, RenderFormat.PDF);
        System.out.println(pdfOutput);
        System.out.println("\n");
        
        System.out.println("The same template was successfully rendered in HTML, PDF, and CSV formats!");
    }
}
