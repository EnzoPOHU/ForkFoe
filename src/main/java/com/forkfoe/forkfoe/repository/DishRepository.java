package com.forkfoe.forkfoe.repository;

import com.forkfoe.forkfoe.util.SQLiteWrapper;
import com.forkfoe.forkfoe.model.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class DishRepository {
    private static List<Dish> dishs;

    static {
        dishs = fetchDishs();
    }

    public static List<Dish> getDish() {
        return dishs;
    }

    /**
     * Fetch all dishes from the database in descending order by price
     */
    public static List<Dish> fetchDishs() {
        try {
            return SQLiteWrapper.execute("SELECT * FROM tableDish").stream()
                    .map(row -> {
                        String name = (String) row[1];
                        String description = (String) row[2];
                        int price = (Integer) row[3];
                        String imgPath = (String) row[4];

                        return new Dish(name, description, price, imgPath);
                    })
                    .sorted((d1, d2) -> Integer.compare(d2.getPrice(), d1.getPrice()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des plats depuis la base de données : " + e.getMessage());
            return List.of();
        }
    }


    /*
     * Add a dish
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
     * Remove an existing dish
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
