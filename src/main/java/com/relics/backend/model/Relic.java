package com.relics.backend.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
public class Relic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String identification;
    public String description;

    @OneToOne
    @Cascade(CascadeType.PERSIST)
    public GeographicLocation geographicLocation;

    @OneToMany
    public Set<Category> categories;

    public String registerNumber;
    public String datingOfObject;

    @OneToMany
    public Set<Review> reviews;

    @OneToMany
    public Set<Image> images;

    public Relic() {
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeographicLocation getGeographicLocation() {
        return geographicLocation;
    }

    public void setGeographicLocation(GeographicLocation geographicLocation) {
        this.geographicLocation = geographicLocation;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getDatingOfObject() {
        return datingOfObject;
    }

    public void setDatingOfObject(String datingOfObject) {
        this.datingOfObject = datingOfObject;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
