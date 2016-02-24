package holo.com.response.core;

import holo.com.response.core.session.HttpSession;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ¸ùÉî on 2016/2/11.
 */
public class ResponseHeader {
    final static String Set_Cookie = "Set-Cookie";
    final static String STATE_OK = "HTTP/1.1 200 OK";
    final static String Redirect = "HTTP/1.1 302 Found";
    public final static String NOT_FOUND = "HTTP/1.1 404 NotFound";
    String first_line = STATE_OK;
    Map<String, String> heads = new HashMap<>();

    public void Out(BufferedOutputStream bos) {
        try {
            bos.write((first_line + "\r\n").getBytes());
            Iterator<Map.Entry<String, String>> en = heads.entrySet().iterator();
            Map.Entry<String, String> m;
            while (en.hasNext()) {
                m = en.next();
                bos.write((m.getKey() + ":" + m.getValue() + "\r\n").getBytes());
            }
            bos.write("\r\n".getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCookie(HttpSession session) {
        if (session.session_id.isEmpty()) {
            heads.put(Set_Cookie, HttpSession.SESSION_ID + "=" + session.create());
            return;
        }
    }

    public void setState(String line){
        first_line = line;
    }

    public void setHeadValue(String key, String value) {
        heads.put(key, value);
    }
}
