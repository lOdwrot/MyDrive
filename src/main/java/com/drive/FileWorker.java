package com.drive;

import java.util.concurrent.Callable;

/**
 * Created by lodwr on 02.12.2015.
 */
public class FileWorker implements Callable{

    private FileSender fileSender;
    String  path;
    public FileWorker(String path,FileSender fileSender) {
        this.fileSender = fileSender;
        this.path = path;
    }
    public Void call() throws Exception {
        fileSender.send(path);
        return null;
    }
}
