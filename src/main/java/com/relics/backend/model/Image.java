package com.relics.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public boolean isMainImage;

    public String pathToImage;

    @ManyToOne
    public Relic relic;

    public Image() {
    }
}
