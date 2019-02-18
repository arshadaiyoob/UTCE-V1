package com.izar.dev.utce.RecyclerAdapter;

public class FeedData {
    private String feedname;
    private String thumbnail;
    private String shortdes;
    private String fulldes;
    private String author;
    private String date;

    public FeedData() {
    }

    public FeedData(String feedname, String thumbnail,String shortdes,String fulldes,String author,String date) {
        this.feedname = feedname;
        this.thumbnail = thumbnail;
        this.shortdes= shortdes;
        this.fulldes = fulldes;
        this.author = author;
        this.date = date;
    }

    public String getfeedname() {
        return feedname;
    }

    public void setfeedname(String name) {
        this.feedname = name;
    }

    public String getshortdes() {
        return shortdes;
    }

    public void setshortdes(String sd) {
        this.shortdes = sd;
    }

    public String getfulldes() {
        return fulldes;
    }

    public void setfulldes(String fd) {
        this.fulldes = fd;
    }

    public String getauthor() {
        return author;
    }

    public void setauthor(String author) {
        this.author = author;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}

