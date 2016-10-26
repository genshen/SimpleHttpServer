package me.gensh.request.data;

/**
 * Created by cgs  on 2016/2/22.
 */
public class GetData extends BasicData {

    /**
     * produce param from Request Url( like  a=2&id=6)
     *
     * @param tail tail can empty or contains char '#'
     */
    public GetData(String tail) {
        String[] d = tail.split("#")[0].split("&|=");
        int length = d.length / 2;
        for (int i = 0; i < length; i++) {
            data.put(d[2 * i], d[2 * i + 1]);
        }
    }
}
