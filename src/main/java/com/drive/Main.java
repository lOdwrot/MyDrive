package com.drive;

import com.dropbox.core.*;

import java.io.*;

/**
 * Created by lodwr on 02.12.2015.
 */
public class Main {

    public static void main(String [] args) throws InterruptedException, DbxException, IOException {
        MainForm mf= new MainForm();
//        final ConfigService c = new ConfigService("C:\\Users\\lodwr\\IdeaProjects\\MyDrive\\src\\main\\resources\\config");
//        final DropboxConfig dbc = new DropboxConfig();
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                FileListener f = null;
//                try {
//                    f = new FileListener("D:\\ListenIt",new FileSender(dbc.getClient()),2);
//                } catch (DbxException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    f.listen();
//                } catch (NotDirectoryException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();
    }
}
