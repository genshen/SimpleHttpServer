package holo.com.response.core;

import holo.com.request.RequestHeader;
import holo.com.request.data.GetData;
import holo.com.request.data.PostData;
import holo.com.response.core.render.HtmlRender;
import holo.com.response.core.render.LayoutRender;
import holo.com.response.core.session.HttpSession;
import holo.com.tools.URL;
import holo.com.tools.json.JSONObject;

import java.io.*;

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

    public void badRequest() {
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

    /**just rend a string to browser*/
    public void render(String i) {
        responseHead.Out(bos);
        try {
            bos.write((i).getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(String template,LayoutRender.Layout layout, JSONObject data) {
        responseHead.Out(bos);
        HtmlRender html = new HtmlRender(template,data,layout,bos);
        html.render();
    }

    public void render(String template, JSONObject data) {
        render(template,LayoutRender.DEFAULT_LAYOUT, data);
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