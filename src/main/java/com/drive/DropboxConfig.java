package com.drive;

import com.dropbox.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Created by lodwr on 03.12.2015.
 */
public class DropboxConfig {

    final String APP_KEY = "e0p2sfoilasj533";
    final String APP_SECRET = "i4rxux8yo3n4c63";
    private String myToken = "lARWXKFGjUAAAAAAAAAAG40oT3sLlkn_h_OcXz6olIjTtlxKncXksGyN5wVWRlAc";

    public String getToken() throws IOException, DbxException {
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxRequestConfig config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();


        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

       // System.out.println(accessToken);
        return accessToken;
    }
    public DbxClient getClient() throws DbxException {
        DbxRequestConfig config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        DbxClient client = new DbxClient(config, myToken);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
        return client;
    }
    public DbxClient getClient(String token) throws DbxException {
        DbxRequestConfig config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        DbxClient client = new DbxClient(config, token);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
        return client;
    }

}
