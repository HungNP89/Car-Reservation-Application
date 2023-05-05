package com.example.carreservationapplication.models;

public class CategoryCarModel {
    String image;
    String name;
    String type;

    public CategoryCarModel() {

    }

    public CategoryCarModel(String image, String name, String type) {
        this.image = image;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
