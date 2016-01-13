package com.drive;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lodwr on 02.12.2015.
 */
public class FileListener {

    private ExecutorService pool;
    private FileSender sender;
    private String path;
    private WatchKey watckKey;
    private boolean listening;

    public FileListener(String path,FileSender sender, int ThresadAmmount)
    {
        pool = Executors.newFixedThreadPool(ThresadAmmount);
        this.sender=sender;
        this.path=path;
    }
    public void listen() throws NotDirectoryException {
        String directoryPath = path;

        if(!Validator.pointDirecory(directoryPath)) {
            throw new NotDirectoryException(directoryPath);
        }
        sendExisted(directoryPath);
        System.out.println("Start listening");
        Path  myDir =  Paths.get(directoryPath);
        try {

            WatchService watcher = myDir.getFileSystem().newWatchService();
            myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                                    StandardWatchEventKinds.ENTRY_DELETE,
                                    StandardWatchEventKinds.ENTRY_MODIFY);

            //WatchKey watckKey = watcher.take();
            watckKey = watcher.take();
            listening=true;
           while(listening) {
            List<WatchEvent<?>> events = watckKey.pollEvents();
                TimeUnit.SECONDS.sleep(1);
                for (WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        String filePath = myDir.toString()+'\\'+event.context();
                        System.out.println(filePath);
                        sendInThread(filePath);
                    }


                }

           }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void sendInThread(String filePath){
        pool.submit(new FileWorker(filePath,sender));

    }
    private void sendExisted(String path)
    {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for(File f : listOfFiles)
        {
            sendInThread(f.getPath());
        }
    }
    public void stopListen()
    {
        listening=false;
        pool.shutdown();
    }

}
