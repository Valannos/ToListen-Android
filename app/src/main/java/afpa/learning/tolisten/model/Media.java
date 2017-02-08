package afpa.learning.tolisten.model;

/**
 * Created by Afpa on 08/02/2017.
 */

public class Media {

    private int id;
    private String title, url, author, genre, user;

    public Media(int id, String title, String url, String author, String genre, String user) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.author = author;
        this.genre = genre;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
