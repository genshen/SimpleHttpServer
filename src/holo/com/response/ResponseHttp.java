package holo.com.response;


import holo.com.request.RequestHeader;
import holo.com.request.RequestLineFirst;
import holo.com.request.RequestType;
import holo.com.response.error.NotFoundError;
import holo.com.tools.StringTools;

import java.io.*;
import java.text.Normalizer;

/**
 * Created by cgs on 2015/12/31.
 */
public class ResponseHttp {
    String request_url;
    String BasePath = "F:\\HttpFiles";
    final RequestType requestType;

    public ResponseHttp(RequestHeader rh) {
        request_url = rh.getRequestLineFirst().getRequestUri();
        requestType = rh.getRequestLineFirst().getRequestType();
    }


    public void startResponse(OutputStream outputStream) {
        if (requestType != RequestType.MEDIA) {
            BuiltTextResponse(outputStream);
        } else {
            BuiltMediaResponse(outputStream);
        }
    }

    private void BuiltMediaResponse(OutputStream os) {
        File file = new File(BasePath + request_url);
        try {
            if (!file.exists()) {
                new NotFoundError(os);
                return;
            }
            FileInputStream in_s = new FileInputStream(file);
            byte byt[] = new byte[2048];

            int length;
            os.write("HTTP/1.1 200 OK\r\nLast-Modified: Thu, 22 Jan 2015 19:53:38 GMT\r\n\r\n".getBytes());
            while ((length = in_s.read(byt)) != -1) {
                os.write(byt, 0, length);
            }
            in_s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("send finished\t" + request_url);
    }

    private void BuiltTextResponse(OutputStream os) {
        File file = new File(BasePath + request_url);
        if (!file.exists()) {
            NotFoundError notFound = new NotFoundError(os);
            if(requestType == RequestType.HTML){
                notFound.render(request_url);
            }
            return;
        }
        try {
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader br_r = new BufferedReader(is_r);

            String line;
            os.write("HTTP/1.1 200 OK\r\nLast-Modified: Thu, 22 Jan 2015 19:53:38 GMT\r\n\r\n".getBytes());
            while ((line = br_r.readLine()) != null) {
                os.write(line.getBytes("UTF-8"));
            }
            br_r.close();
            is_r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("send finished\t" + request_url);
    }
}
