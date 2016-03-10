package holo.com.response.controllers;

import holo.com.request.RequestHeader;
import holo.com.response.core.MediaController;
import holo.com.response.core.session.HttpSession;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by cgs on 2016/3/4.
 */
public class Audio extends MediaController {

    public Audio(OutputStream os, RequestHeader header,HttpSession session) {
        super(os,header, session);
    }

    public void audioMedia() {
        File f = new File("F:/HttpFiles/public/media/music.mp3");
        pullOut(f);
    }
}
