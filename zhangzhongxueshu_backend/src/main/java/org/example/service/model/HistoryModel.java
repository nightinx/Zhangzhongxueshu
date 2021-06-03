package org.example.service.model;

public class HistoryModel {
    private Integer id;

    private String email;

    private String articleid;
    private String time;

    public Integer getId() {
        return id;
    }

    public String getArticleid() {
        return articleid;
    }

    public String getEmail() {
        return email;
    }

    public String getTime() {
        return time;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
