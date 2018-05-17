package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Entity
@SequenceGenerator(name = "sequence", initialValue = 1)
public class Relic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @JsonView(View.BasicDescription.class)
    public Long id;

    @Column(columnDefinition="TEXT")
    @JsonView(View.BasicDescription.class)
    public String identification;

    @Column(columnDefinition="TEXT")
    @JsonView(View.BasicDescription.class)
    public String description;

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.PERSIST)
    public GeographicLocation geographicLocation;

    @ManyToMany
    public Set<Category> categories;

    @JsonView(View.BasicDescription.class)
    public String registerNumber;

    @JsonView(View.BasicDescription.class)
    public String datingOfObject;

    @OneToMany
    public Set<Review> reviews;

    @OneToMany
    //@JoinColumn(name="relic_id")
    @Cascade(CascadeType.PERSIST)
    public Set<Image> images;

    public Relic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Relic{" +
                "id=" + id +
                ", identification='" + identification + '\'' +
                ", description='" + description + '\'' +
                ", geographicLocation=" + geographicLocation +
                ", categories=" + categories +
                ", registerNumber='" + registerNumber + '\'' +
                ", datingOfObject='" + datingOfObject + '\'' +
                ", reviews=" + reviews +
                ", images=" + images +
                '}';
    }
}
