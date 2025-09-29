package com.dsantos;

import com.dsantos.renderer.CsvRenderer;
import com.dsantos.renderer.HtmlRenderer;
import com.dsantos.renderer.PdfRenderer;
import com.dsantos.renderer.TemplateRenderer;

import java.util.HashMap;
import java.util.Map;

public class TemplateEngine {
    public enum RenderFormat {
        HTML, PDF, CSV
    }

    private final Map<RenderFormat, TemplateRenderer> renderers;

    public TemplateEngine() {
        this.renderers = new HashMap<>();
        renderers.put(RenderFormat.HTML, new HtmlRenderer());
        renderers.put(RenderFormat.CSV, new CsvRenderer());
        renderers.put(RenderFormat.PDF, new PdfRenderer());
    }

    public String render(Template template, RenderFormat format) {
        TemplateRenderer renderer = renderers.get(format);
        if (renderer == null) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        return renderer.render(template);
    }
}
