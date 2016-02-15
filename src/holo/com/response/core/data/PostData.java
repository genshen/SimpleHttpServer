package holo.com.response.core.data;

import holo.com.request.HttpReader;
import holo.com.tools.StringTools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ¸ùÉî on 2016/2/14.
 */
public class PostData {
    final static int FileReaderBufferSize = 1024;
    final static String ContentDisposition = "Content-Disposition";
    String multiDivLine = "";
    long dataLength;
    Map<String, String> data = new HashMap<>();
    HttpReader reader;

    public PostData(HttpReader reader, String contentType, long dataLength) {
        this.reader = reader;
        this.dataLength = dataLength;
        if (contentType.contains("multipart")) {
            int position = contentType.lastIndexOf("boundary");
            if (position == -1) return;
            multiDivLine = contentType.substring(position + 9); // 9 = "boundary".length()+ 1    /* char = */
            setMultipartField();
        } else {
            setNormalData();
        }
    }

    private void setNormalData() {
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

}
