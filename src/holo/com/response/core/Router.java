package holo.com.response.core;

/**
 * Created by cgs on 2016/1/2.
 */
public class Router {
    final static char RouteDiv = '/';
    String controller = "", added_path, action = "";

    /**
     * constructor for media resource
     */
    public Router(String url) {
        char[] u = url.toCharArray();
        int len = 0;
        for (int i = u.length - 6; i >= 0; i--) {
            if (u[i] == RouteDiv) {
                if (action.isEmpty()) {
                    action = new String(u, i + 1, len);
                    len = 0;
                    continue;
                }
                u[i + 1] = u[i + 1] < 0x5a ? u[i + 1] : (char) (u[i + 1] - 0x20);//index to Index
                controller = new String(u, i + 1, len);
                added_path = new String(u, 0, i);
                break;
            }
            len++;
        }
        check();
    }

    private void check() {
        if (action.isEmpty()) {
            action = Config.Router.defaultAction;
        }
        if (controller.isEmpty()) {
            controller = Config.Router.defaultController;
        }
    }

    /**
     * constructor for media resource
     *
     * @param url url
     * @param o   null usually
     */
    public Router(String url, Object o) {
        if (url == null || url.length() < 1) {
            check();
            return;
        }
        char[] u = url.toCharArray();
        int start = u[0] == RouteDiv ? 1 : 0;
        for (int i = start; i < u.length; i++) {
            if (u[i] == RouteDiv) {
                if (controller.isEmpty()) {
                    u[start] = u[start] < 0x5a ? u[start] : (char) (u[start] - 0x20);
                    controller = new String(u, start, i - start);
                    start = i + 1;
                    continue;
                }
                action = new String(u, start, i - start);
                added_path = new String(u, i, u.length - i);//include char '/'
                break;
            }
        }
        check();
    }
}
