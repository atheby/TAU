package org.atheby.tau.restassured.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

    private long id;
    private long postId;
    private String author;
    private String text;

    public Comment() {}

    public Comment(long postId, String author, String text) {
        this.postId = postId;
        this.author = author;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
