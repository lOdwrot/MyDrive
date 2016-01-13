package com.drive;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lodwr on 09.12.2015.
 */
public class DropboxConfigTest {
    private String correctToken="lARWXKFGjUAAAAAAAAAAG40oT3sLlkn_h_OcXz6olIjTtlxKncXksGyN5wVWRlAc";
    private final String incorrectToken ="ABC";
    DropboxConfig dc = new DropboxConfig();


    @Test
    public void shouldReturnDbxClientForCorrectToken() throws DbxException {
        assertTrue(dc.getClient(correctToken).getClass().equals(DbxClient.class));
    }
    @Test(expected = NullPointerException.class)
    public void shouldGetNullPointerExceptionForNull() throws DbxException {
        dc.getClient(null);
    }
    @Test(expected = DbxException.class)
    public void shouldGetDbxExceptionForIncorrectToken() throws DbxException {
        dc.getClient(incorrectToken);
    }


}