package me.gensh.response.core.render;

import me.gensh.response.core.Config;
import me.gensh.utils.Network;
import me.gensh.utils.json.JSONObject;

import java.io.*;

/**
 * Created by cgs on 2016/2/14.
 */
public abstract class HtmlRender {
    BufferedOutputStream bos;
    String template;
    JSONObject data;

    HtmlRender() {
    }

    HtmlRender(String template, JSONObject data, BufferedOutputStream bos) {
        this.bos = bos;
        this.template = template;
        this.data = data;
    }

    /**
     * render template with a layout
     */
    public abstract void render();

    public void setTemplate(String template){
        this.template = template;
    }

    public void setData(JSONObject data){
        this.data = data;
    }

    public void bindOutputStream(BufferedOutputStream bos){
        this.bos = bos;
    }
}

