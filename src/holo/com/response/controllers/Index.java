package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.Controller;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.json.JSONArray;
import holo.com.tools.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by cgs on 2016/2/12.
 */
public class Index extends Controller {
    String string;

    public Index(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
    }

    public void indexAction() {
        String name = getParams().getString("name");
        JSONObject data = new JSONObject();
        if(name.isEmpty()){
            name = "World";
        }
        data.put("title", "Main  page");
        data.put("name", name);
        render("index/index.html", data);
    }

    public void imagesAction() {
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
        render("index/images.html", data);
    }

    public void statusAction() {
        JSONObject json = new JSONObject();
        json.put("title","status TEST");
        render("index/status.html",  json);
    }

    public void redirectAction() {
        redirect("https://baidu.com");
    }
}
