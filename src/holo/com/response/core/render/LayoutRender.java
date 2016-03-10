package holo.com.response.core.render;

import java.io.*;

/**
 * Created by 根深 on 2016/3/10.
 */
public class LayoutRender {
    /*you can add other layout here,just like DEFAULT_LAYOUT */
    public final static Layout DEFAULT_LAYOUT = new Layout("F:/HttpFiles/core/views/layout/main.html",
            new int[]{3, 54, 8});
    final static byte[] newline = {'\r', '\n'};
    int which = 0;
    Layout layout;
    BufferedOutputStream bos;
    BufferedReader br_r;

    public LayoutRender(Layout layout, BufferedOutputStream bos) {
        this.layout = layout;
        this.bos = bos;
        try {
            InputStreamReader is_r = new InputStreamReader(new FileInputStream(layout.layout_path));
            br_r = new BufferedReader(is_r);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void continueRender() {
        if (which >= layout.layout_pieces.length) {
            return;
        }
        String line;
        for (int k = layout.layout_pieces[which]; k > 0; k--) {
            try {
                line = br_r.readLine();
                bos.write(line.getBytes());
                bos.write(newline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        which++;
    }

    public void finishRender() {
        String line;
        try {
            while ((line = br_r.readLine()) != null) {
                bos.write(line.getBytes());
                bos.write(newline);
            }
            br_r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Layout {
        String layout_path;
        int[] layout_pieces;

        private Layout(String path, int[] pieces) {
            layout_path = path;
            layout_pieces = pieces;
        }
    }
}
