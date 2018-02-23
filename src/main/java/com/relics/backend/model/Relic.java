package com.relics.backend.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Relic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String placeName;
    public String communeName;
    public String districtName;
    public String voivodeshipName;
    public Double latitude;
    public Double longitude;

    //TODO: Why I cannot persist category by defualt??!!
    @OneToMany
    public Set<Category> categories;

//    @OneToMany
//    @Cascade(CascadeType.PERSIST)
//    public Set<Review> reviews;

    public Relic() {
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVoivodeshipName() {
        return voivodeshipName;
    }

    public void setVoivodeshipName(String voivodeshipName) {
        this.voivodeshipName = voivodeshipName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

//    public void addReview(Review review) {
//        reviews.add(review);
//    }
//
//    public Set<Review> getReviews() {
//        return reviews;
//    }
}
