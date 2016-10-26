package me.gensh.response.core;

import me.gensh.request.RequestHeader;
import me.gensh.request.RequestType;
import me.gensh.response.controllers.Errors;
import me.gensh.response.core.session.HttpSession;
import me.gensh.response.error.NotFoundError;
import me.gensh.router.Router;
import me.gensh.utils.StringTools;

import java.io.*;

/**
 * Created by cgs on 2015/12/31.
 */
public class ResponseHttp {
    private String requestUrl;
    private RequestHeader header;
    private final RequestType requestType;

    public ResponseHttp(RequestHeader rh) {
        header = rh;
        requestUrl = rh.getRequestLineFirst().getRequestUri();
        requestType = rh.getRequestLineFirst().getRequestType();
    }

    public void startResponse(OutputStream outputStream) {
        if (requestUrl.startsWith(Config.AssetsFilePrefix)) {
            BuiltStaticResponse(outputStream);
        } else {
            if (requestType == RequestType.MEDIA) {
                generateMedia(outputStream);
            } else {
                renderHtml(outputStream);
            }
        }
    }

    private void BuiltStaticResponse(OutputStream os) {
        String basePath = Config.BasePath;
        File file = new File(basePath + requestUrl);
        if (!file.exists()) {
            new NotFoundError(os);
            return;
        }
        try {
            long lastModify = file.lastModified();
            if (CheckModify(os, lastModify)) {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                byte byt[] = new byte[2048];
                int length;
                os.write(("HTTP/1.1 200 OK\r\nLast-Modified: " + StringTools.formatModify(lastModify) + "\r\n\r\n").getBytes());
                while ((length = bis.read(byt)) != -1) {
                    os.write(byt, 0, length);
                }
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("send finished\t" + requestUrl);
    }

    private void generateMedia(OutputStream os) {
        ResponseMediaInterface ri = me.gensh.router.Router.mediaRouters.get("/");
        HttpSession session = new HttpSession(header.getHeaderValueByKey(HttpSession.Cookie));
        if (ri != null) {
            MediaController con = new MediaController(os, header, session);
            ri.OnResponse(con);
        }else{
            new NotFoundError(os);
        }
    }

    private void renderHtml(OutputStream os) {
        ResponseInterface ri = Router.routers.get("/");
        HttpSession session = new HttpSession(header.getHeaderValueByKey(HttpSession.Cookie));
        if (ri != null) {
            Controller con = new Controller(os, header, session);
            ri.OnResponse(con);
        }else{
            Errors error404 = new Errors(os, header, session);
            error404.notFoundAction(requestUrl, true);
        }
    }

    private boolean CheckModify(OutputStream os, long lastModify) {
        if (!StringTools.CheckModify(header.getHeaderValueByKey("If-Modified-Since"), lastModify)) {
            try {
                os.write(("HTTP/1.1 304 Not Modified\r\n\r\n").getBytes());
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
