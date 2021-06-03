package org.example.service.model;

public class CommentModel {
    private Integer id;

    private String articleid;

    private String time;

    private String email;

    private String content;

    public Integer getId() {
        return id;
    }

    public String getArticleid() {
        return articleid;
    }

    public String getContent() {
        return content;
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

    public void setContent(String content) {
        this.content = content;
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
