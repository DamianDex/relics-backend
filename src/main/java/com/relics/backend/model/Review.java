package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToMany
    public Relic relic;

    @ManyToOne
    public AppUser appUser;

    public Integer rating;
    public String comment;

    public Review() {
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
