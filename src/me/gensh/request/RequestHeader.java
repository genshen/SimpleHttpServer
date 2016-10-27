package me.gensh.request;


import me.gensh.request.data.PostData;
import me.gensh.utils.StringTools;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cgs on 2015/12/31.
 */
public class RequestHeader {
    RequestLineFirst requestLineFirst;
    Map<String, String> Header = new HashMap<>();
    PostData postData;
    final protected static String Content_Length = "Content-Length";
    final protected static String Content_Type = "Content-Type";

    public RequestHeader(Socket clientSocket) {
        try {
            InputStream is = clientSocket.getInputStream();
            HttpReader httpReader = new HttpReader(is);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            requestLineFirst = new RequestLineFirst(httpReader.readLine());
            if (requestLineFirst.isHttp()) {
                setHeader(httpReader);
                if (requestLineFirst.getMethod() == RequestLineFirst.POST) {
                    setData(httpReader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * set POST data,if the method is RequestLineFirst.POST.
     *
     * @param reader stream from client
     */
    private void setData(HttpReader reader) {
        postData = new PostData(reader, Header.get(Content_Type),
                Long.parseLong(Header.get(Content_Length)));
    }

    /**
     * separate every request line as key-value,and save it in a map
     *
     * @param reader stream from client
     */
    private void setHeader(HttpReader reader) {
        String line;
        while (!(line = reader.readLine()).isEmpty()) {
            String str[] = line.split(":", 2);
            if (str.length == 2) {
                Header.put(str[0], str[1].trim());
            }
        }
    }

    public String getHeaderValueByKey(String key) {
        return Header.get(key);
    }

    public RequestLineFirst getRequestLineFirst() {
        return requestLineFirst;
    }

    public PostData getPostData() {
        return postData;
    }
    /**
     * first line of http request
     */
    public class RequestLineFirst {
        String requestUri;
        public String requestTail = "";
        boolean isHttp = false;
        byte method;
        final static byte GET = 0;
        public final static byte POST = 1;

        /**
         * the first line just like {@code GET /bootstrap/css/bootstrap.min.css HTTP/1.1}
         *
         * @param s the first line string
         *          set method,url
         */
        public RequestLineFirst(String s) {
            if (s == null) return;
            String Re[] = s.split(" ");
            if (Re.length == 3) {
                isHttp = true;
                method = setMethod(Re[0]);
                String getUrl[] = Re[1].split("[?]", 2);
                requestUri = StringTools.NormalizeUrl(getUrl[0]);
                requestTail = getUrl.length == 2 ? getUrl[1] : "";
            }
        }

        /**
         * set value {method},from http first line
         *
         * @param method method string
         * @return method: GET or POST.
         */
        private byte setMethod(String method) {
            if (method.equalsIgnoreCase("POST")) {
                return POST;
            }
            return GET; // as default
        }

        /**
         * @return normally GET or POST.
         */
        public byte getMethod() {
            return this.method;
        }

        public String getRequestUri() {
            return this.requestUri;
        }

        public boolean isHttp() {
            return isHttp;
        }
    }
}
