package holo.com.response.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ¸ùÉî on 2016/2/11.
 */
public class ResponseHeader {
    final static String STATE_OK = "HTTP/1.1 200 OK";
    String first_line = STATE_OK;
    Map<String,String> heads = new HashMap<>();
}
