package me.gensh.response.core.render;

import holo.com.response.core.Config;
import holo.com.tools.Network;
import holo.com.tools.json.JSONObject;

import java.io.*;

/**
 * Created by cgs on 2016/2/14.
 */
public class HtmlRender {
    final static byte[] BASE = ("<base href='http://" + Network.HOST + ":" + Network.HttpPort + "/'/>").getBytes();
    BufferedOutputStream bos;
    LayoutRender layoutRender;
    String template;
    JSONObject data;
    final static byte[] newline = {'\r', '\n'};

    public HtmlRender(String template, JSONObject data, LayoutRender.Layout layout, BufferedOutputStream bos) {
        this.bos = bos;
        this.template = template;
        this.layoutRender = new LayoutRender(layout,bos);
        this.data = data;
    }

    /**
     * render template with a layout
     */
    public void render() {
        layoutRender.continueRender();
        sendHtmlHead();
        layoutRender.continueRender();
        sendTemplate(template);
        layoutRender.continueRender();
        sendData(data);
        layoutRender.finishRender();
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(template);
    }

    private void sendHtmlHead() {
        try {
            bos.write(BASE);
            bos.write(newline);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData(JSONObject data) {
        JSONObject json = new JSONObject();
        json.put("el", "html");
        json.put("data", data);
        try {
            bos.write(json.toString().getBytes());
            bos.write(newline);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendTemplate(String template) {
        try {
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(Config.View.VIEW + template));
            BufferedReader br_r = new BufferedReader(is_r);
            while ((template = br_r.readLine()) != null) {
                bos.write(template.getBytes());
                bos.write(newline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

