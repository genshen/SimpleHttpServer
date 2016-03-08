package holo.com.response.core;

import holo.com.request.RequestHeader;
import holo.com.response.core.data.GetData;
import holo.com.response.core.data.PostData;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.StringTools;
import holo.com.tools.URL;
import holo.com.tools.json.JSONObject;
import holo.com.tools.json.JSONString;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ���� on 2016/2/12.
 */
public class Controller {
    private GetData data_get = null;
    public BufferedOutputStream bos;
    public ResponseHeader responseHead;
    public RequestHeader requestHeader;
    public HttpSession session;

    public Controller(OutputStream os, RequestHeader header, HttpSession session) {
        bos = new BufferedOutputStream(os);
        this.responseHead = new ResponseHeader();
        this.session = session;
        this.requestHeader = header;
        responseHead.setCookie(session);
    }

    public void redirect(String controller, String action) {
        responseHead.setState(ResponseHeader.Redirect);
        responseHead.setHeadValue("Location", URL.url(controller, action));
        responseHead.setHeadValue("Content-Length", "0");
        responseHead.Out(bos);
    }

    public void forbidden() {
        responseHead.setState(ResponseHeader.FORBIDDEN);
        responseHead.Out(bos);
    }

    public void notFound() {
        responseHead.setState(ResponseHeader.NOT_FOUND);
        responseHead.Out(bos);
    }

    public void badRequese() {
        responseHead.setState(ResponseHeader.Bad_Request);
        responseHead.Out(bos);
    }


    public GetData getParams() {
        if (data_get == null) {
            data_get = new GetData(requestHeader.getRequestLineFirst().requestTail);
        }
        return data_get;
    }

    public boolean isPost() {
        return requestHeader.getRequestLineFirst().getMethod() == RequestHeader.RequestLineFirst.POST;
    }

    public PostData getPostData() {
        return requestHeader.getPostData();
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

    @Deprecated
    public void render() {
        responseHead.Out(bos);
        HtmlRender html = new HtmlRender(bos);
        html.renderLayout();
    }

    public void render(String template, JSONObject data) {
        responseHead.Out(bos);
        HtmlRender html = new HtmlRender(template, data, bos);
        html.render();
    }

    public void renderJSON(String json) {
        responseHead.setHeadValue(ResponseHeader.Content_Type, "application/json; charset=utf-8");
        responseHead.setHeadValue(ResponseHeader.Content_Length, "" + json.length());
        responseHead.Out(bos);
        try {
            bos.write((json).getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outFile(String path) {
        byte[] b = new byte[1024];
        responseHead.Out(bos);
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            while ((bis.read(b)) != -1) {
                bos.write(b);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}