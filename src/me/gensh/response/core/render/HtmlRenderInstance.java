package me.gensh.response.core.render;


import me.gensh.response.core.Config;
import me.gensh.utils.Network;
import me.gensh.utils.json.JSONObject;

import java.io.*;

/**
 * Created by gensh on 2016/10/26.
 */
public class HtmlRenderInstance extends HtmlRender {
    private final static byte[] BASE = ("<base href='http://" + Network.HOST + ":" + Network.HttpPort + "/'/>").getBytes();
    private final static byte[] newline = {'\r', '\n'};
    //if we use layout
    private LayoutRender layoutRender;
    private final static LayoutRender.Layout layout = new LayoutRender.Layout(Config.BasePath + Config.View.LAYOUT + "main.html", new int[]{3, 9, 6});

    public HtmlRenderInstance(String template, JSONObject data, BufferedOutputStream bos) {
        super(template, data, bos);
        this.layoutRender = new LayoutRender(layout, bos);
    }

    @Override
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
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(Config.BasePath + Config.View.VIEW + template));
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
