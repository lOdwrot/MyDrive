package com.drive;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lodwr on 02.12.2015.
 */
public class FileSender {
    AtomicInteger stats = new AtomicInteger(0);

    private List<String> errors;
    private DbxClient client;
    private DropBoxSender dbs;
    public FileSender(DbxClient client)
    {
        this.client=client;
        errors= Collections.synchronizedList(new ArrayList<String>());
        dbs=new DropBoxSender();
    }
    public void send(String path){
        try {
            sendFile(new File(path));
        } catch (IOException e) {
            errors.add(e.getLocalizedMessage());
        } catch (DbxException e) {
            errors.add(e.getLocalizedMessage());
        }
    }

    public int getStats(){
        return stats.getAndSet(0);
    }
    public List<String> getErrors(){
        return errors;
    }
    public void clearErrors()
    {
        errors.clear();
    }

    private void sendFile(File f) throws IOException, DbxException {
        dbs.send(client,f);
        stats.incrementAndGet();
//        File inputFile = f;
//        FileInputStream inputStream = new FileInputStream(inputFile);
//        try {
//            DbxEntry.File uploadedFile = client.uploadFile('/'+inputFile.getName(),
//                    DbxWriteMode.add(), inputFile.length(), inputStream);
//            System.out.println("Uploaded: " + uploadedFile.toString());
//        } finally {
//            inputStream.close();
//        }
    }
}
