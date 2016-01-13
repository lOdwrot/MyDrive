package com.drive;

import java.io.File;

/**
 * Created by lodwr on 02.12.2015.
 */
public class Validator {
    public static boolean pointDirecory(String path)
    {
        File f = new File(path);
        return f.isDirectory();
    }
    public static boolean isNotEmptyText(String in)
    {
        return (!in.isEmpty());
    }
}
