package com.example.lablnet.mediaplayer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DATA MODEL CLASS
 */

public class Data {
    String fileTitle;
    String duration;
    String albumID;
    String path;

    public String getFileTitle() {
        return fileTitle;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbumID() {
        return albumID;
    }

    public String getPath() {
        return path;
    }

    public String getSize() {
        return size;
    }

    String size;

    public Data(String fileTitle, String duration, String albumID, String path, String size) {
        this.fileTitle = fileTitle;
        this.duration = duration;
        this.albumID = albumID;
        this.path = path;
        this.size = size;
    }

    public static double parseMbs(String size) {
        double bytes = Double.parseDouble(size);
        double sizeInMB = Math.round(bytes / 1048576);
        return sizeInMB;
    }

    public static String FormatTime(String Time)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm:ss");
        Date  date = new Date(Long.parseLong(Time));
        return simpleDateFormat.format(date);
    }
}
