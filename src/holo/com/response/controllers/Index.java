package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.Controller;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.json.JSONArray;
import holo.com.tools.json.JSONObject;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by ¸ùÉî on 2016/2/12.
 */
public class Index extends Controller {
    String string;

    public Index(OutputStream os, RequestHeader header,HttpSession session) {
        super(os,header, session);
    }

    public void indexAction() {
        JSONObject data = new JSONObject();
        data.put("title","Main  page");
        render("index/index.html", data);
    }

    public void videoAction() {
        JSONObject data = new JSONObject();
        data.put("title", "Watch Video");
        data.put("path", getParams().getString("path"));
        render("index/video.html", data);
    }
}
