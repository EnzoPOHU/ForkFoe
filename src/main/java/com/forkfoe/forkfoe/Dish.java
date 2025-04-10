package com.forkfoe.forkfoe;

import javafx.scene.image.Image;

public class Dish {
    public String name;
    public String description;
    public int price;
    public Image img;
    public String imagePath;

    public Dish(String name, String description, int price, Image img) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getPrice() {
        return price;
    }
}
