package me.gensh.core.response;

import me.gensh.core.request.RequestHeader;
import me.gensh.core.response.session.HttpSession;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * Created by cgs on 2016/2/24.
 */
public class MediaController extends Controller {
    final static String Content_Range = "Content-Range";
    long range_start = 0, range_end = -1;
    final static long MAX_TRANSFER = 512 * 1024;
    boolean responseAble = false;
    String reg = "[0-9]+-[0-9]*";

    public MediaController(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
        String range = header.getHeaderValueByKey("Range");
        if (range == null) {
            return;
        }
        //just think about "123-"  and "123-123" format.
        range = range.substring(6);//    bytes=0-
        if (range.matches(reg)) {
            responseAble = true;
            String[] s = range.split("-", 2);
            range_start = Long.parseLong(s[0]);
            range_end = s[1].isEmpty() ? -1 : Long.parseLong(s[1]);
        }
    }

    /**
     * read file from byte: range_start to range_end
     *
     * @param file
     */
    public void pullOut(File file) {
        if (!responseAble) {
            badRequest();
            return;
        }
        long length = file.length();
        if (range_end == -1) {
            range_end = length;
        }
        range_end = min(length-1, min(range_end, range_start + MAX_TRANSFER));
        if (range_start >= 0 && range_start <= range_end) {
            byte[] b = new byte[2014];
            responseHead.setState(ResponseHeader.Http206);
            responseHead.setHeadValue(ResponseHeader.Content_Length, "" + (range_end - range_start+1));
            responseHead.setHeadValue(Content_Range, "bytes " + range_start + "-" + range_end + "/" + length);
            responseHead.Out(bos);
            try {
                RandomAccessFile rf = new RandomAccessFile(file, "r");
                rf.seek(range_start--);
                while ((length = rf.read(b)) != -1) {
                    range_start += length;
                    if (range_start >= range_end) {
                        length -= (range_start - range_end);
                        bos.write(b, 0, (int) length);
                        break;
                    }
                    bos.write(b);
                }
                bos.flush();
                rf.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            //416 error.
        }
    }

    private long min(long l, long r) {
        return l < r ? l : r;
    }

}
