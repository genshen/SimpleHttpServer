package me.gensh.request.data;

import holo.com.response.core.Config;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by cgs on 2016/2/14.
 */
public class FileUploadManager {
    public static byte[] NewLine = {'\r', '\n'};
    FileOutputStream fos;
    BufferedOutputStream bos;
    boolean created = false;
    boolean last_new_line = false;

    public FileUploadManager() {
//        create();
    }

    public void create(String temp_filename) {
        if (created) return;
        try {
            fos = new FileOutputStream(Config.TempPath + temp_filename);
            bos = new BufferedOutputStream(fos);
            created = true;
            last_new_line = false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (!created) return;
        try {
            bos.flush();
            bos.close();
            fos.close();
            created = false;
            last_new_line = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] buff, int size, boolean new_line) {
        if (!created) return;
        try {
            if (last_new_line) {
                bos.write(FileUploadManager.NewLine, 0, 2);
            }
            last_new_line = new_line;
            bos.write(buff, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
