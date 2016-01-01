package holo.com.request;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by cgs on 2015/12/31.
 */
public class RequestHeader {

    RequestLineFirst requestLineFirst;
    public RequestHeader(Socket clientSocket) {
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            requestLineFirst = new RequestLineFirst(reader.readLine());
            /* while (!(line = reader.readLine()).isEmpty()) {
                if(line.contains("Referer")){   //case
                    String str[] = line.split("/",4);
                    referer = new Referer(str.length >= 4 ?  str[3]:null);
                }
                System.out.println(line);
            }
             */
//            reader.close();
//            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Referer getReferer() {
//        return referer;
//    }


    public RequestLineFirst getRequestLineFirst() {
        return requestLineFirst;
    }
}
