package com.example.carreservationapplication.models;

import java.io.Serializable;

public class CategoryCarListModel implements Serializable {
    String name;
    String description;
    String image;
    String type;
    int price;
    String condition;

    public CategoryCarListModel() {

    }

    public CategoryCarListModel(String name, String description, String image, String type, int price, String condition) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
        this.price = price;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCondition() { return condition;}

    public void setCondition(String condition) { this.condition = condition;}
}
