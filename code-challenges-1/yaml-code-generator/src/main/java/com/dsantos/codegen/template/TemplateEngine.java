package com.dsantos.codegen.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateEngine {

    private final Configuration cfg;

    public TemplateEngine() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_33);
        cfg.setClassForTemplateLoading(TemplateEngine.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    public String render(String templateName, Map<String, Object> model) {
        try {
            Template template = cfg.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to render template: " + templateName, e);
        }
    }
}

