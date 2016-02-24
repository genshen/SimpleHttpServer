package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.Controller;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.json.JSONArray;
import holo.com.tools.json.JSONObject;

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
        data.put("title", getParams().getString("hi"));
        JSONArray array = new JSONArray("[{ text: 'Learn JavaScript' },{ text: 'Learn Vue.js' },"
                + "{ text: 'Build Something Awesome' }]");
        data.put("todos", array);
        render("index/index.html", data);
    }
}
