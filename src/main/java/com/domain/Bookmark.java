package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;

    private String uri;
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    /*@JsonIgnore
    @ManyToOne
    private Author author;*/

    public Bookmark() {}    // jpa only

    public Bookmark(String uri, String description, User user) {
        this.uri = uri;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*public Author getAuthor() {
        return user;
    }

    public void setAuthor(Author author) {
        this.user = user;
    }*/
}