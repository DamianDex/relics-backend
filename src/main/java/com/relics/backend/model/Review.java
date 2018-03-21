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

    @ManyToOne
    public Relic relic;

    @ManyToOne
    @JsonView(View.BasicDescription.class)
    public AppUser appUser;

    @JsonView(View.BasicDescription.class)
    public Integer rating;

    @JsonView(View.BasicDescription.class)
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
