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
        if (requestUrl.startsWith(Config.StaticFilePrefix)) {
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
        File file = new File(Config.BasePath + requestUrl);
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
        } else {
            new NotFoundError(os);
        }
    }

    private void renderHtml(OutputStream os) {
        ResponseInterface ri = Router.routers.get("/");
        HttpSession session = new HttpSession(header.getHeaderValueByKey(HttpSession.Cookie));
        if (ri != null) {
            Controller con = new Controller(os, header, session);
            ri.OnResponse(con);
            if(Config.DebugMod){   //debug mod
                System.out.println(requestUrl+"\n"+con.responseHead.first_line);
            }
        } else {
            Errors error404 = new Errors(os, header, session);
            error404.notFoundAction(requestUrl, true);
            if(Config.DebugMod){  //debug mod
                System.out.println(requestUrl+"\n"+error404.responseHead.first_line);
            }
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
