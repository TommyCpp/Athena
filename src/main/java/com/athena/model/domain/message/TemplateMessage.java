package com.athena.model.domain.message;

import com.athena.model.User;
import com.athena.util.TemplateBuilder;
import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * Created by Tommy on 2017/12/24.
 */
public class TemplateMessage implements Message {
    private String title;
    private Map<String, String> templateParas;
    private Resource templateFile;
    private TemplateBuilder templateBuilder;
    private User sender;

    public TemplateMessage(String title, Map<String, String> templateParas, Resource templateFile, TemplateBuilder templateBuilder, User sender) {
        this.title = title;
        this.templateParas = templateParas;
        this.templateFile = templateFile;
        this.templateBuilder = templateBuilder;
        this.sender = sender;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTemplateParas(Map<String, String> templateParas) {
        this.templateParas = templateParas;
    }

    public void setTemplateFile(Resource templateFile) {
        this.templateFile = templateFile;
    }

    public void setTemplateBuilder(TemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Override
    public String getContent() {
        return templateBuilder.build(this.templateFile, templateParas);
    }

    @Override
    public User getSender() {
        return this.sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

}
