package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.Controller;
import holo.com.response.core.ResponseHeader;
import holo.com.response.core.session.HttpSession;
import holo.com.response.error.NotFoundError;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by cgs on 2016/3/2.
 */
public class Download extends Controller{
    public Download(OutputStream os, RequestHeader header,HttpSession session) {
        super(os,header,session);
    }

    public void downloadMedia() {
        String path = getParams().getString("path");
        File f = new File(path);
        if(!f.exists()){ // file not exist
            new NotFoundError(bos);
            return;
        }
        responseHead.setHeadValue(ResponseHeader.Content_Length, f.length()+"");
        responseHead.setHeadValue(ResponseHeader.Content_Transfer_Encoding, "binary");
        responseHead.setHeadValue(ResponseHeader.Content_Type, "application/octet-stream");
        outFile(path);
    }
}
