package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.MediaController;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.json.JSONObject;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by cgs on 2016/3/4.
 */
public class User extends MediaController {

    public User(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
    }

    public void indexAction() {
        boolean loginStatus = session.getSessionBoolean("login", false);
        if (loginStatus) { //如果是登录状态
            JSONObject json = new JSONObject();
            json.put("title", "Enjoy It");
            json.put("username", (String) session.getSession("username"));
            render("user/index.html", json);
            return;
        } else {  //否则重定向至登录界面
            redirect("user", "login");
        }
    }

    public void loginAction() {
        boolean loginStatus = session.getSessionBoolean("login", false);
        if (loginStatus) { //如果是登录状态
            redirect("user", "index");
        }
        JSONObject data = new JSONObject();
        boolean show_error = false;
        if (isPost()) {
            String username = getPostData().getString("username");
            String password = getPostData().getString("password");
            if ("123456".equals(password) && "chugenshen".equals(username)) {
                session.setSession("username", username);
                session.this_seesion.put("login", true);
                redirect("user", "index");
                return;
            }
            show_error = true;
        }
        data.put("show_error", show_error);
        data.put("title", "Login");
        render("user/login.html", data);
    }

}
