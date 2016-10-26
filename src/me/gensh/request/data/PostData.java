package me.gensh.request.data;

import me.gensh.request.HttpReader;
import me.gensh.tools.StringTools;

import java.util.Map;

/**
 * Created by cgs on 2016/2/14.
 */
public class PostData extends BasicData{
    private final static int FileReaderBufferSize = 1024;
    private final static String ContentDisposition = "Content-Disposition";
    private String multiDivLine = "";
    private long dataLength;
    private boolean hasMultiReceived = true;
    private HttpReader reader;

    public PostData(HttpReader reader, String contentType, long dataLength) {
        this.reader = reader;
        this.dataLength = dataLength;
        if (contentType.contains("multipart")) {
            int position = contentType.lastIndexOf("boundary");
            if (position == -1) return;
            multiDivLine = contentType.substring(position + 9); // 9 = "boundary".length()+ 1    /* char = */
            hasMultiReceived = false;
        } else {
            setNormalData();
        }
    }

    private void setNormalData() {
        if (dataLength > FileReaderBufferSize)
            return;
        byte[] buff = new byte[(int) dataLength];
        try {
            int len = reader.read(buff);
            String d = new String(buff, "utf-8");
            String[] v = d.split("&|=");
            int length = v.length / 2;
            for (int i = 0; i < length; i++) {
                data.put(v[2 * i], v[2 * i + 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMultipartField() {
        boolean data_processing = true, is_file = false;
        long read_count = 0;
        int buff_size;
        String name = "";
        byte[] buff = new byte[FileReaderBufferSize];
        FileUploadManager fileU = new FileUploadManager();

        do {
            buff_size = reader.readLine(buff, FileReaderBufferSize - 1);
            read_count += buff_size;
            if (data_processing) {
                if (buff[0] == '-' && StringTools.getStringByBytesUTF8(buff, buff_size).contains(multiDivLine)) {
                    data_processing = false;
                    is_file = false;
                    read_count += 2;
                    fileU.close();
                    continue;
                }
                if (is_file) {
                    if (buff_size == FileReaderBufferSize) {
                        fileU.write(buff, buff_size, false);
                    } else {
                        fileU.write(buff, buff_size, true);
                        read_count += 2;
                    }
                } else {  // normal data
                    if (buff_size != FileReaderBufferSize)
                        read_count += 2;
                    data.put(name, StringTools.getStringByBytesUTF8(buff, buff_size));
                }
            } else {   // as default, the length of normal data is smaller than FileReaderBufferSize.
                read_count += 2;
                if (buff_size == 0) {
                    data_processing = true;
                    continue;
                }
                String line = StringTools.getStringByBytesUTF8(buff, buff_size);
                if (line.startsWith(ContentDisposition)) {
                    String[] cache = line.split("=|;");
                    if (cache.length >= 3) {
                        name = cache[2].replaceAll("\"", "");
                        if (cache.length >= 5) {
                            String filename = cache[4].replaceAll("\"", "");
                            if (!filename.isEmpty()) {
                                is_file = true;
                                filename = System.currentTimeMillis() + filename;
                                data.put(name, filename);
                                fileU.create(filename);
                            }
                        }
                    }
                }
            }
        } while (read_count < dataLength);
    }

    /**
     * receive Multipart data(usually,it's file data)
     */
    private void fill() {
        if (!hasMultiReceived){
            setMultipartField();
            hasMultiReceived = true;
        }
    }

    public Map getAllData() {
        fill();
        return data;
    }

    public String getString(String name) {
        fill();
        return super.getString(name);
    }

    public int getInt(String name) {
        fill();
        return super.getInt(name);
    }

}
