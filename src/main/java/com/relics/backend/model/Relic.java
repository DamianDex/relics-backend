package com.relics.backend.model;

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
}
