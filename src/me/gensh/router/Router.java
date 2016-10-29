package me.gensh.router;

import me.gensh.controllers.Index;
import me.gensh.controllers.Media;
import me.gensh.core.response.ResponseInterface;
import me.gensh.core.response.ResponseMediaInterface;

import java.util.TreeMap;

/**
 * Created by gensh on 2016/10/26.
 */
public class Router {
    public static TreeMap<String, ResponseInterface> routers = new TreeMap<>();
    public static TreeMap<String, ResponseMediaInterface> mediaRouters = new TreeMap<>();

    public Router() {
        //add your router here
        Add("/", Index.indexAction);
        Add("/media", Media.IndexAction);
    }

    private void Add(String uri, ResponseInterface callback) {
        routers.put(uri, callback);
    }

    private void Add(String uri, ResponseMediaInterface callback) {
        mediaRouters.put(uri, callback);
    }
}
