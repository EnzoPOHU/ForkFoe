package com.forkfoe.forkfoe;

import javafx.scene.image.Image;

import java.io.File;

public class Dish {
    public String name;
    public String description;
    public int price;
    public Image img;
    public String imagePath;

    public Dish(String name, String description, int price, String imgPath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imgPath;

        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                File imageFile = new File("data/" + new File(imgPath).getName());
                if (imageFile.exists()) {
                    this.img = new Image(imageFile.toURI().toString(), true);
                } else {
                    System.err.println("L'image n'a pas été trouvée dans le dossier data : '" + imageFile.getPath() + "'");
                }
            } catch (Exception e) {
                System.err.println("Exception lors du chargement de l'image : " + e.getMessage());
            }
        }
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
