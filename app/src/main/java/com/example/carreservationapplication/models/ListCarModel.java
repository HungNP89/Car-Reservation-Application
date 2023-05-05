package com.example.carreservationapplication.models;

import androidx.navigation.ActionOnlyNavDirections;

import java.io.Serializable;

public class ListCarModel implements Serializable {
    String image;
    String name;
    int price;
    String description;
    String type;
    String condition;

    public ListCarModel() {

    }

    public ListCarModel(String image, String name, int price, String description, String type, String condition) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.condition = condition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() { return condition;}

    public void setCondition(String condition) { this.condition = condition;}
}
