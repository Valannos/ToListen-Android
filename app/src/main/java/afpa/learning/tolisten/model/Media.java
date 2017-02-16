package afpa.learning.tolisten.model;

import android.os.Bundle;

/**
 * Created by Afpa on 08/02/2017.
 */

public class Media {

    private int id;
    private String title, url, author, genre, sender;
    private boolean isViewed;

    public Media(int id, String title, String url, String author, String genre, String sender, boolean isViewed) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.author = author;
        this.genre = genre;
        this.sender = sender;
        this.isViewed = isViewed;
    }

    public Media(String title, String url, String author, String genre, String sender) {
        this.sender = sender;
        this.url = url;
        this.author = author;
        this.genre = genre;
        this.title = title;
        this.isViewed = false;
    }

    public Media(Bundle extras) {
        this.id = extras.getInt("id");
        this.title = extras.getString("title");
        this.url = extras.getString("url");
        this.author = extras.getString("author");
        this.genre = extras.getString("genre");
        this.sender = extras.getString("sender");
        this.isViewed = extras.getBoolean("isViewed");
    }

    public Media(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String user) {
        this.sender = user;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", sender='" + sender + '\'' +
                ", isViewed=" + isViewed +
                '}';
    }
}
