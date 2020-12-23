package com.example.project3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

public class PageAction implements Serializable {

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Page page;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Action> actions;

    public PageAction() {
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
