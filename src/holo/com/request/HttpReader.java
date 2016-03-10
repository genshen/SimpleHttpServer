package holo.com.request;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cgs  on 2015/12/31.
 */
public class HttpReader extends BufferedInputStream {

    byte[] b = new byte[1024];

    public HttpReader(InputStream is) {
        super(is);
    }

    /**
     * read a line from BufferedInputStream , normally it end with '\n' ,and followed with '\r';
     *
     * @return a line
     */
    public String readLine() {
        byte pre, next;
        int i = 0;
        try {
            next = (byte) read();  //read first byte as init.
            for (; ; ) {
                pre = next;
                if (pre == -1) {
                    return new String(b, 0, i);
                }
                next = (byte) read();
                if (pre == '\r' && next == '\n') {//10 13
                    return new String(b, 0, i);
                }
                b[i++] = pre;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * read a line from the buff, if the length of line is longer than byte array's length,
     * return number is byte array's size.
     *
     * @param buff byte array to fill content(a line).
     * @return length of line. -1 for full array size without '\r\n' ending
     */
    public int readLine(byte[] buff, int length) {
        byte pre, next = 0;
        int i = 0;
        try {
            next = (byte) read();
            while (i < length) {
                pre = next;
//                if (pre == -1) {
//                    return -1;
//                }
                next = (byte) read();
                if (pre == '\r' && next == '\n') {
                    return i;
                }
                buff[i++] = pre;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buff[i++] = next;
        return i;
    }
}
