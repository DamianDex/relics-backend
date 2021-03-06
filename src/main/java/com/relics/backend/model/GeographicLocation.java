package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GeographicLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @JsonView(View.BasicDescription.class)
    public Double latitude;
    @JsonView(View.BasicDescription.class)
    public Double longitude;
    @JsonView(View.BasicDescription.class)
    public String voivodeshipName;
    @JsonView(View.BasicDescription.class)
    public String districtName;
    @JsonView(View.BasicDescription.class)
    public String communeName;
    @JsonView(View.BasicDescription.class)
    public String placeName;
    @JsonView(View.BasicDescription.class)
    public String street;

    public GeographicLocation() {
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

    public String getVoivodeshipName() {
        return voivodeshipName;
    }

    public void setVoivodeshipName(String voivodeshipName) {
        this.voivodeshipName = voivodeshipName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "GeographicLocation{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", voivodeshipName='" + voivodeshipName + '\'' +
                ", districtName='" + districtName + '\'' +
                ", communeName='" + communeName + '\'' +
                ", placeName='" + placeName + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}