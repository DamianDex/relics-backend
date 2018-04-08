package com.relics.backend.model;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public boolean isMainImage;

    public String pathToImage;

    @ManyToOne
    //@JoinColumn(name="relic_id")
    public Relic relic;

    public Image() {
    }

    public boolean isMainImage() {
        return isMainImage;
    }

    public void setMainImage(boolean mainImage) {
        isMainImage = mainImage;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }
}
