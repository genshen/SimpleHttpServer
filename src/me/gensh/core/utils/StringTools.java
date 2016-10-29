package me.gensh.core.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cgs on 2015/12/31.
 */
public class StringTools {
    /**
     * /good/t/  -> /good/t/
     * /good/t  -> /good/t/
     * good/t  -> /good/t
     *
     * @param url url
     */
    public static String NormalizeUrl(String url) {
        char u[] = url.toCharArray();
        if (u.length > 0 && u[0] != '/') {
            return "/" + url;
        } else if (u.length >= 2 && u[u.length - 1] == '/') {
            return new String(u, 0, u.length - 1);
        }
        return url;
    }

    public static String getStringByBytesUTF8(byte[] buff, int buff_size) {
        return getStringByBytes(buff, buff_size, "utf-8");
    }

    public static String getStringByBytes(byte[] b, int length, String charset) {
        byte[] _b = new byte[length];
        System.arraycopy(b, 0, _b, 0, length);
        try {
            return new String(_b, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getExtension(String name) {
        char u[] = name.toCharArray();
        StringBuilder extension = new StringBuilder();
        for (int i = u.length - 1; i >= 0 && u[i] != '.'; i--) {
            extension.insert(0, u[i]);
        }
        return extension.toString();
    }

    /**
     * check whether the file has modified after modifyTime
     *
     * @param modifyTime modifyTime from browser
     * @param lastmodify the file last modify
     * @return true for has modified, false for not modified
     */
    public static boolean CheckModify(String modifyTime, long lastmodify) {
        if (modifyTime == null) {
            return true;
        }
        try {
            // SimpleDateFormat是非线程安全的,每次用的时候new一个,同样：formatModify方法
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            Date d = sdf.parse(modifyTime);
            long date = d.getTime() / 1000;
            return date < lastmodify / 1000;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    /**
     * change long format to  format{ EEE, dd MMM yyyy HH:mm:ss z}
     *
     * @param l
     * @return format string
     */
    public static String formatModify(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        Date d = new Date(l);
        return sdf.format(d);
    }

}
