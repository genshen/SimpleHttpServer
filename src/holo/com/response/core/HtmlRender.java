package holo.com.response.core;

import holo.com.tools.Network;
import holo.com.tools.json.JSONObject;

import java.io.*;

/**
 * Created by ¸ùÉî on 2016/2/14.
 */
public class HtmlRender {
    final static byte[] BASE = ("<base href='http://" + Network.HOST + ":" + Network.HttpPort + "/'/>").getBytes();
    BufferedOutputStream bos;
    String template;
    JSONObject data;
    byte[] newline = {'\r', '\n'};

    public HtmlRender(String template, JSONObject data, BufferedOutputStream bos) {
        this.bos = bos;
        this.template = template;
        this.data = data;
    }

    public HtmlRender(BufferedOutputStream bos) {
        this.bos = bos;
    }

    @Deprecated
    public void renderLayout() {
        String line;
        try {
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(Config.View.VIEW_LAYOUT));
            BufferedReader br_r = new BufferedReader(is_r);
            while ((line = br_r.readLine()) != null) {
                bos.write(line.getBytes());
                bos.write(newline);
            }
            br_r.close();
            is_r.close();
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render() {
        try {
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(Config.View.VIEW_LAYOUT));
            BufferedReader br_r = new BufferedReader(is_r);
            String line;
            sendLayout(Config.View.VIEW_LAYOUT_TOP, br_r);
            sendHtmlHead();
            sendLayout(Config.View.VIEW_LAYOUT_HEADER, br_r);
            sendTemplates(template);
            sendLayout(Config.View.VIEW_LAYOUT_BREAK, br_r);
            sendData(data);
//            send out left content
            while ((line = br_r.readLine()) != null) {
                bos.write(line.getBytes());
                bos.write(newline);
            }
            br_r.close();
            is_r.close();
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

    private void sendLayout(short lines, BufferedReader br_r) {
        String line;
        for (short k = lines; k > 0; k--) {
            try {
                line = br_r.readLine();
                bos.write(line.getBytes());
                bos.write(newline);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void sendTemplates(String template) {
        try {
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(Config.View.VIEW + template));
            BufferedReader br_r = new BufferedReader(is_r);
            while ((template = br_r.readLine()) != null) {
                bos.write(template.getBytes());
                bos.write(newline);
            }
//            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

