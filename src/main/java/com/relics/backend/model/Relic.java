package com.relics.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Relic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Relic() {
    }
}
