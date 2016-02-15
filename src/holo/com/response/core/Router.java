package holo.com.response.core;

/**
 * Created by ¸ùÉî on 2016/1/2.
 */
public class Router {
    final static char RouteDiv = '/';
    String controller = "", added_path, action = "";

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
                u[i + 1] = u[i + 1]  < 0x5a ? u[i + 1] : (char) (u[i + 1] - 0x20);//index to Index
                controller = new String(u, i + 1, len);
                added_path = new String(u, 0, i);
                break;
            }
            len++;
        }
        if (action.isEmpty()) {
            action = Config.Router.defaultAction;
        }
        if (controller.isEmpty()) {
            controller = Config.Router.defaultController;
        }
    }
}
