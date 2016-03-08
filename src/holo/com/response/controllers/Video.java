package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.MediaController;
import holo.com.response.core.session.HttpSession;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by ¸ùÉî on 2016/2/24.
 */
public class Video extends MediaController {
    public Video(OutputStream os, RequestHeader header,HttpSession session) {
        super(os,header,session);
    }

    public void videoMedia() {
        System.out.println("video in");
        File f = new File(getParams().getString("path"));
        pullOut(f);
    }

}
