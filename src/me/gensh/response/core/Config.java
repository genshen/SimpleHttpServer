package me.gensh.response.core;

/**
 * Created by gensh on 2016/2/11.
 */
public class Config {
    public final static String BasePath = "E:/Workspace/Java/SimpleHttpServer";
    public final static String TempPath = "F:/HttpFiles/temp/";
    public final static String StaticFilePrefix = "/static";
    public final static Boolean DebugMode = true;

    @Deprecated
    private class Router {
        final static String defaultController = "Index";
        final static String defaultAction = "index";
    }

    @Deprecated
    public class ControllerConfig {
        final static String ControllerPackage = "holo.com.response.controllers.";
        final static String Action = "Action"; //can't change!
        final static String Media = "Media"; //can't change!
    }

    final static public class View {
        public final static String VIEW = "/views/";
    }

}
