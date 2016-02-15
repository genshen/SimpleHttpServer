package holo.com.response.core;

/**
 * Created by ¸ùÉî on 2016/2/11.
 */
public class Config {
    public final static String BasePath = "F:/HttpFiles";
    public final static String TempPath = "F:/HttpFiles/temp/";

    public class Router {
        final static String defaultController = "Index";
        final static String defaultAction = "index";
    }

    public class ControllerConfig {
        final static String ControllerPackage = "holo.com.response.controllers.";
        final static String Action = "Action"; //can't change!
    }

    public class View {
        //        final static  String resource ="public/";
        final static String VIEW = "F:/HttpFiles/core/views/";
        final static String VIEW_LAYOUT = "F:/HttpFiles/core/views/layout/main.html";
        final static short VIEW_LAYOUT_TOP = 3;
        final static short VIEW_LAYOUT_HEADER = 39;
        final static short VIEW_LAYOUT_BREAK = 7;
    }


}
