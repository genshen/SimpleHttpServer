package me.gensh.response.controllers;

import me.gensh.request.RequestHeader;
import me.gensh.response.core.Controller;
import me.gensh.response.core.ResponseInterface;
import me.gensh.response.core.session.HttpSession;
import me.gensh.utils.json.JSONArray;
import me.gensh.utils.json.JSONObject;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by cgs on 2016/2/12.
 */
public class Index {

    public static ResponseInterface indexAction = context -> {
        String name = context.getParams().getString("name");
        JSONObject data = new JSONObject();
        if (name.isEmpty()) {
            name = "World";
        }
        data.put("title", "Main  page");
        data.put("name", name);
        context.render("index/index.html", data);
    };

    public static ResponseInterface imageAction = context -> {
        JSONObject data = new JSONObject();
        data.put("title", "Images");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            if (i <= 27) {
                list.add(i + ".jpg");
            } else {
                list.add(i + ".png");
            }
        }
        data.put("images", new JSONArray(list));
        context.render("index/images.html", data);
    };

    public static ResponseInterface statusAction = context -> {
        JSONObject json = new JSONObject();
        json.put("title", "status TEST");
        context.render("index/status.html", json);
    };

    public static ResponseInterface redirectAction = context -> {
        context.redirect("https://baidu.com");
    };

}
