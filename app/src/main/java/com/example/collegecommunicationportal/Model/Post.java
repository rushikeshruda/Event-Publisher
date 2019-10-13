package com.example.collegecommunicationportal.Model;

/**
 * Created by Ulan on 07.12.2018.
 */
public class Post {
    private String authorID,title, college, time, date,entryFee,id;

    public Post() {}

    public Post(String authorID,String college,String date, String entryFee, String id, String time, String title) {

        this.authorID = authorID;
        this.id = id;
        this.title = title;
        this.college = college;
        this.entryFee = entryFee;
        this.time = time;
        this.date = date;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
