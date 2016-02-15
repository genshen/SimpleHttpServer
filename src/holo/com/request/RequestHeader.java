package holo.com.request;


import holo.com.response.core.data.PostData;
import holo.com.tools.StringTools;

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
        PostData postData = new PostData(reader, Header.get(Content_Type),
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

    /**
     * first line of http request
     */
    public class RequestLineFirst {
        String requestUri;
        boolean isHttp = false;
        RequestType requestType;
        byte method;
        final static byte GET = 0;
        final static byte POST = 1;

        /**
         * the first line just like {@code GET /bootstrap/css/bootstrap.min.css HTTP/1.1}
         *
         * @param s the first line string
         *          set method requestType(html,json,css,js,medal...),url
         */
        public RequestLineFirst(String s) {
            if (s == null) return;
            String Re[] = s.split(" ");
            if (Re.length == 3) {
                isHttp = true;
                method = setMethod(Re[0]);
                String getUrl[] = Re[1].split("[?]", 2);
                requestUri = StringTools.NormalizUrl(getUrl[0]);
                requestType = StringTools.getRequestType(requestUri);
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

        public RequestType getRequestType() {
            return requestType;
        }
    }
}
