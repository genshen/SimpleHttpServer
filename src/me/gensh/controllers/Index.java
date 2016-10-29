package me.gensh.controllers;

import me.gensh.core.response.ResponseInterface;
import me.gensh.core.utils.json.JSONArray;
import me.gensh.core.utils.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cgs on 2016/2/12.
 */
public class Index {

    public static ResponseInterface indexAction = context -> {
        String name = context.getParams().getString("name");
        Map<String,String> data = new HashMap<>();
        if (name.isEmpty()) {
            name = "World";
        }
        data.put("title", "Dome page");
        data.put("name", name);
        context.render("test.ftl", data);
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
