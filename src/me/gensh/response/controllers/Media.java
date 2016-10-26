package me.gensh.response.controllers;

import me.gensh.request.RequestHeader;
import me.gensh.response.core.*;
import me.gensh.response.core.session.HttpSession;
import me.gensh.utils.json.JSONObject;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by cgs on 2016/2/24.
 */
public class Media extends MediaController {
    public Media(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
    }

    public static ResponseMediaInterface IndexAction = context -> {
        JSONObject json = new JSONObject();
        json.put("title", "Video And Audio");
        context.render("media/index.html", json);
    };

    public static ResponseMediaInterface videoMedia = context -> {
        File f = new File(Config.BasePath + context.getParams().getString("path"));
        context.pullOut(f);
    };

    public static ResponseMediaInterface audioMedia = context -> {
        File f = new File(Config.BasePath + context.getParams().getString("path"));
        context.pullOut(f);
    };

}
