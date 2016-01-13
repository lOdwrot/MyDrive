package com.drive;

import java.util.List;

/**
 * Created by lodwr on 04.12.2015.
 */
public class StatsService {
    private FileSender sender;

    public StatsService(FileSender sender) {
        this.sender =sender;
    }

    public int getStats(){
        return sender.getStats();
    }
    public List<String> getErrorsList()
    {
        return sender.getErrors();
    }
    public void clearErrorLis()
    {
        sender.clearErrors();
    }
}
