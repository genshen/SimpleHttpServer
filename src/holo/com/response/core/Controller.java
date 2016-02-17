package holo.com.response.core;

import holo.com.response.core.session.HttpSession;
import holo.com.tools.StringTools;
import holo.com.tools.json.JSONObject;
import holo.com.tools.json.JSONString;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ¸ùÉî on 2016/2/12.
 */
public class Controller {
    public BufferedOutputStream bos;
    public ResponseHeader responseHead;
    public HttpSession session;

    public Controller(OutputStream os, HttpSession session) {
        bos = new BufferedOutputStream(os);
        this.responseHead = new ResponseHeader();
        this.session = session;
        responseHead.setCookie(session);
    }

    public void render(String i) {
        responseHead.Out(bos);
        try {
            bos.write((i).getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(String template, JSONObject data) {
        responseHead.Out(bos);
        HtmlRender html = new HtmlRender(template, data, bos);
        html.render();
    }

}