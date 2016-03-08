package holo.com.response.error;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cgs on 2016/1/1.
 */
public class NotFoundError {
    OutputStream os;

    public NotFoundError(OutputStream os) {
//           System.out.println("file not exit:" + request_url);
        this.os = os;
        try {
            os.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(String request_url) {
        try {
            os.write(("<html><head><title>Not found Error</title></head>"
                    +"<body><h1>"+request_url + " not found</h1></body></html>").getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
