package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @JsonView(View.BasicDescription.class)
    @JsonInclude
    public ApplicationUser appUser;

    @JsonView(View.BasicDescription.class)
    public String comment;

    @JsonView(View.BasicDescription.class)
    private String creationDate;

    public ReviewComment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "ReviewComment{" +
                "id=" + id +
                ", appUser=" + appUser +
                ", comment='" + comment + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}

