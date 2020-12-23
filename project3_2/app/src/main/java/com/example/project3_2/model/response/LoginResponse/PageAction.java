package com.example.project3_2.model.response.LoginResponse;

import java.io.Serializable;
import java.util.List;

public class PageAction implements Serializable {

    private Page page;

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