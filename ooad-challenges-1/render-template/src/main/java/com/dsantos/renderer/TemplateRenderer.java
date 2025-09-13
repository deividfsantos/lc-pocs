package com.dsantos.renderer;

import com.dsantos.Template;

public interface TemplateRenderer {
    String render(Template template);
    String getFileExtension();
}
