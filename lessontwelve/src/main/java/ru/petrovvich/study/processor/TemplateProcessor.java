package ru.petrovvich.study.processor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessor {
    private static final String HTML_DIR = "/tml/";

    private Configuration configuration;

    public TemplateProcessor(Configuration configuration) {
        this.configuration = configuration;
        configuration.setClassForTemplateLoading(this.getClass(), HTML_DIR);
        configuration.setDefaultEncoding("UTF-8");
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);

            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
