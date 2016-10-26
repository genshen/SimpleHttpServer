package me.gensh.response.core;

/**
 * Created by cgs on 2016/1/2.
 */
@Deprecated
public class Router {
    final static char RouteDiv = '/';
    final static char RoutePoint = '.';
    String controller = "", added_path, action = "";


    /**
     * constructor for media resource
     *
     * @param url url
     */
    public Router(String url) {
        if (url == null || url.length() < 1) {
            check();
            return;
        }
        char[] u = url.toCharArray();
        int start = u[0] == RouteDiv ? 1 : 0;
        for (int i = start; i < u.length; i++) {
            if (u[i] == RouteDiv || u[i] == RoutePoint) {
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

    private void check() {
        if (action.isEmpty()) {
            action = Config.Router.defaultAction;
        }
        if (controller.isEmpty()) {
            controller = Config.Router.defaultController;
        }
    }

}
