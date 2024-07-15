package com.projects.urbancart.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "product")
public class Product extends BaseModel {

    private String title;
    private String description;
    private double price;
    private String image;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private int quantity;

}
