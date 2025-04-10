package com.forkfoe.forkfoe;

import javafx.scene.image.Image;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DishRepository {
    private static List<Dish> dishs;

    static {
        dishs = fetchDishs();
    }

    /*
    static list for get only once dishs
     */
    public static List<Dish> getDish() {
        return dishs;
    }

    /*
    get all dishs in database
     */
    public static List<Dish> fetchDishs() {
        try {
            return SQLiteWrapper.execute("SELECT * FROM tableDish").stream()
                    .map(row -> {
                        String name = (String) row[1];
                        String description = (String) row[2];
                        int price = (Integer) row[3];
                        String imgPath = (String) row[4];

                        Dish dish = new Dish(name, description, price, imgPath);
                        return dish;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des plats depuis la base de données : " + e.getMessage());
            return List.of();
        }
    }

    /*
    method to create a dish
     */
    public static void addDish(Dish dish) {
        try {
            SQLiteWrapper.execute(
                    "INSERT INTO tableDish (name, description, price, img) VALUES (?,?,?,?)",
                    dish.name,
                    dish.description,
                    dish.price,
                    dish.imagePath
            );
            dishs.add(dish);
        } catch (Exception e) {
            System.err.println("Échec de l'ajout du plat : " + e.getMessage());
        }
    }

    /*
    method to remove one dish with the name
     */
    public static void removeDish(Dish dish) {
        try {
            SQLiteWrapper.execute("DELETE FROM tableDish WHERE name = ?", dish.name);
            dishs.remove(dish);
        } catch (Exception e) {
            System.err.println("Échec de la suppression du plat : " + e.getMessage());
        }
    }
}
