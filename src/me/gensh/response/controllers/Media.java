package me.gensh.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.Config;
import holo.com.response.core.MediaController;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.json.JSONObject;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by cgs on 2016/2/24.
 */
public class Media extends MediaController {
    public Media(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
    }

    public void indexAction() {
        JSONObject json = new JSONObject();
        json.put("title", "Video And Audio");
        render("media/index.html", json);
    }

    public void videoMedia() {
        File f = new File(Config.BasePath + getParams().getString("path"));
        pullOut(f);
    }

    public void audioMedia() {
        File f = new File(Config.BasePath + getParams().getString("path"));
        pullOut(f);
    }

}
