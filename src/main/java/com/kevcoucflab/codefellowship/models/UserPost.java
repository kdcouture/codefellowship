package com.kevcoucflab.codefellowship.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
public class UserPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    AppUser poster;

    Date createdAt;

    @Column(columnDefinition = "text")
    String body;

    public UserPost() {};
    public UserPost(String body, AppUser poster) {
        this.body = body;
        this.createdAt = new Date(new java.util.Date().getTime());
        this.poster = poster;
    }

    public Long getId() {
        return id;
    }

    public AppUser getPoster() {
        return poster;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }
}
