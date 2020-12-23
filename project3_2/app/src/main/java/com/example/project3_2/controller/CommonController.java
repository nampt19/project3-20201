package com.example.project3_2.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.project3_2.activity.LoginActivity;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.response.LoginResponse.Action;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.LoginResponse.PageAction;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CommonController {
    public static final String IP_ADDRESS = "http://192.168.1.230:8080";

    public CommonController() {
    }

    public void checkToken(Activity context) {
        String token = new CachingFile().readCache(context);
        if (token == "") {
            context.startActivity(new Intent(context.getApplicationContext(), LoginActivity.class));
            context.finish();
        }
    }

    public boolean checkReadPage(String res, String pageName, String roleName) {

        LoginResponse loginResponse = new Gson().fromJson(res, LoginResponse.class);
        ArrayList<PageAction> pageActions = (ArrayList<PageAction>) loginResponse.getPageActions();
        for (PageAction pageAction : pageActions) {
            if (pageAction.getPage().getName().equals(pageName)) { // eg pageName = user,transaction,....
                for (Action action : pageAction.getActions()) {
                    if (action.getName().equals(roleName))//eg roleName = create,update,delete,read.
                        //Log.v("create",action.getName());
                        return true;
                }
            }
        }
        return false;
    }

}
