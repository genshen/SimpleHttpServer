package me.gensh.controllers;

import me.gensh.core.request.RequestHeader;
import me.gensh.core.response.Controller;
import me.gensh.core.response.ResponseHeader;
import me.gensh.core.response.session.HttpSession;
import me.gensh.core.utils.json.JSONException;
import me.gensh.core.utils.json.JSONObject;

import java.io.OutputStream;

/**
 * Created by cgs on 2016/1/1.
 */

public class Errors extends Controller {

    public Errors(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
        responseHead.setState(ResponseHeader.NOT_FOUND);
    }

    public void notFoundAction(String url,boolean isHtml) {
        if(!isHtml){
            responseHead.Out(bos);// output 404 state only
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("title", "not found");
            data.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        render("index/error.html", data);
    }
}
