package holo.com.response.core.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by cgs on 2016/2/17.
 */
public class HttpSession {
    public final static String SESSION_ID = "session_id";
    final static int LENGTH_SESSION_ID_NAME = 10;
    public final static String Cookie = "Cookie";
    public static Map<String, Map<String, Object>> sessions = new HashMap<>();
    public Map<String, Object> this_seesion = null;
    public String session_id = "";

    /**
     * @param Cookie Http Cookie String
     */
    //Cookie中没有,则需要setCookie;
    public HttpSession(String Cookie) {
        if (Cookie == null) { //set-Cookie:
            return;
        }
        int index = Cookie.lastIndexOf(SESSION_ID);
        int length = Cookie.length();
        if (index == -1) {
            return;
        }
        index = index + LENGTH_SESSION_ID_NAME;
        for (int i = index; i < length; i++) {
            if (Cookie.charAt(i) == ';') {
                session_id = Cookie.substring(index, i);
                break;
            } else if (i == length - 1) {
                session_id = Cookie.substring(index);
            }
        }
        check();
    }

    /**
     * if session value does not match, we will clear it
     */
    private void check() {
        if (!session_id.isEmpty()) {  // && check format.
            if (sessions.get(session_id) == null) { // add right format session to (sessions)
                Map<String, Object> m = new HashMap<>();
                sessions.put(session_id, m);
            }
        } else {
            session_id = "";
        }
    }

    public void setSession(String name, Object obj) {
        if (name == null || name.isEmpty()) {
            return;
        }
        if (this_seesion == null) {
            this_seesion = sessions.get(session_id);
        }
        this_seesion.put(name, obj);
    }

    /**
     * when call this function after calling
     * {@link holo.com.response.core.Controller#Controller} Constructor,
     * session must exists.
     *
     * @param name NotNull
     * @return Object
     */
    public Object getSession(String name) {
        if (this_seesion == null) {
            this_seesion = sessions.get(session_id);
        }
        return this_seesion.get(name);
    }

    public boolean getSessionBoolean(String name, boolean defaultValue) {
        Object o = getSession(name);
        if (o == null) {
            return defaultValue;
        } else {
            return (boolean) o;
        }
    }

    public int getSessionInt(String name, int defaultValue) {
        Object o = getSession(name);
        if (o == null) {
            return defaultValue;
        } else {
            return (int) o;
        }
    }

    public String create() {
        this.session_id = getRandomString(8); // unique!
        sessions.put(session_id, new HashMap<>());
        return session_id;
    }

    final static String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getRandomString(int length) {

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);//[0,62)

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
