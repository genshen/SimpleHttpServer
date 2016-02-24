package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.Controller;
import holo.com.response.core.ResponseHeader;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.json.JSONException;
import holo.com.tools.json.JSONObject;

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
            data.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        render("index/error.html", data);
    }
}
