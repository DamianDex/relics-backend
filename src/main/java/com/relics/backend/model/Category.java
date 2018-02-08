package com.relics.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {

    @Id
    public String categoryName;

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
