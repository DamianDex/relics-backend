package com.relics.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class GeographicLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Double latitude;
    public Double longitude;
    public String voivodeshipName;
    public String districtName;
    public String communeName;
    public String placeName;
    public String street;

    @OneToOne
    public Relic relic;

    public GeographicLocation() {
    }
}