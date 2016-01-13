package com.drive;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by lodwr on 03.12.2015.
 */
public class DropBoxSender {
    public void send(DbxClient client, File f) throws IOException, DbxException {
        File inputFile = f;
        FileInputStream inputStream = new FileInputStream(inputFile);
            DbxEntry.File uploadedFile = client.uploadFile('/'+inputFile.getName(),
                    DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        inputStream.close();


    }
}
