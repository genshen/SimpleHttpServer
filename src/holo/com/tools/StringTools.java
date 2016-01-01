package holo.com.tools;

import holo.com.request.RequestLineFirst;
import holo.com.request.RequestType;

/**
 * Created by ¸ùÉî on 2015/12/31.
 */
public class StringTools {
    /**
     * /good/  -> /good/index.html
     * /good  -> /good/index.html
     * /good/t.html -> no change
     *
     * @param url
     */
    public static String NormalizUrl(String url) {
        char u[] = url.toCharArray();
        if (u[u.length - 1] == '/') {
            return url + "index.html";
        } else {
            for (int i = u.length - 1; i >= 0 && u[i] != '/'; i--) {
                if (u[i] == '.' && i > 1 && u[i - 1] != '/') { //not like  [node/.html]
                    return url;
                }
            }
            return url + "/index.html";
        }
    }

    public static RequestType getRequestType(String requestUri) {
        String extension = getExtension(requestUri);
        switch (extension.toLowerCase()){
            case "html":
                return RequestType.HTML;
            case "json":
                return RequestType.HTML;
            case "css":
                return RequestType.CSS;
            case "js":
                return RequestType.JS;
            default: return RequestType.MEDIA;
        }
    }

    public static String getExtension(String name){
        char u[] = name.toCharArray();
        StringBuilder extension = new StringBuilder();
        for (int i = u.length - 1; i >= 0 && u[i] != '.'; i--) {
            extension.insert(0, u[i]);
        }
        return extension.toString();
    }
}
