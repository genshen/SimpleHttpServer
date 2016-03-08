package holo.com.response.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 根深 on 2016/2/22.
 */
public class BasicData {
    Map<String, String> data = new HashMap<>();

    public String getString(String name) {
        Object o = data.get(name);
        if (o != null) {
            return o.toString().replaceAll("%20"," ");// space
        }
        return "";
    }

    public int getInt(String name) {
        Object o = data.get(name);
        if (o != null) {
            try {
                return Integer.parseInt(o.toString());
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        return 0;
    }
}
