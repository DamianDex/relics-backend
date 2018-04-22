package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Review {

    @Id
    @JsonView(View.BasicDescription.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    public Relic relic;

    @ManyToOne
    @JsonView(View.BasicDescription.class)
    @JsonInclude
    public ApplicationUser appUser;

    @JsonView(View.BasicDescription.class)
    public Integer rating;

    @JsonView(View.BasicDescription.class)
    public String comment;

    @JsonView(View.BasicDescription.class)
    private String creationDate;

    @OneToMany
    @JsonInclude
    @JsonView(View.BasicDescription.class)
    private List<Vote> votes;

    @OneToMany
    @JsonInclude
    @JsonView(View.BasicDescription.class)
    private List<ReviewComment> comments;

    public Review() {
    }

    public ApplicationUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getDate() {
        return creationDate;
    }

    public void setDate(String date) {
        this.creationDate = date;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<ReviewComment> getComments() {
        return comments;
    }

    public void setComments(List<ReviewComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", relic=" + relic +
                ", appUser=" + appUser +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", votes=" + votes +
                ", comments=" + comments +
                '}';
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void addComment(ReviewComment reviewComment) {
        comments.add(reviewComment);
    }
}
