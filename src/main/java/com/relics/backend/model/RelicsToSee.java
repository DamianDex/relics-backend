package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;

import javax.persistence.*;

@Entity
public class RelicsToSee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonView(View.BasicDescription.class)
    private Relic relic;

    @ManyToOne
    @JsonView(View.BasicDescription.class)
    @JsonInclude
    private ApplicationUser appUser;

    @JsonView(View.BasicDescription.class)
    private boolean relicToSee;

    public RelicsToSee(){
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public ApplicationUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
    }

    public boolean isRelicToSee() {
        return relicToSee;
    }

    public void setRelicToSee(boolean relicToSee) {
        this.relicToSee = relicToSee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RelicsToSee{" +
                "id=" + id +
                ", relic=" + relic +
                ", appUser=" + appUser +
                ", relicToSee=" + relicToSee +
                '}';
    }
}
