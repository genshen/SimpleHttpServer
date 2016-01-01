package holo.com.request;

import holo.com.tools.StringTools;

/**
 * Created by cgs on 2015/12/31.
 */
public class RequestLineFirst {
    String method, requestUri;
    boolean isHttp = false;
    RequestType requestType;

    public RequestLineFirst(String s) {
        if (s == null) return;
        String Re[] = s.split(" ");
        if (Re.length == 3) {
            isHttp = true;
            method = Re[0];
            String getUrl[] = Re[1].split("[?]", 2);
            requestUri = StringTools.NormalizUrl(getUrl[0]);
            requestType = StringTools.getRequestType(requestUri);
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getRequestUri() {
        return this.requestUri;
    }

    public boolean isHttp() {
        return isHttp;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
