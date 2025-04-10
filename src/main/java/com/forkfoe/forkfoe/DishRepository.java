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

    public static List<Dish> getDish() {
        return dishs;
    }

    public static List<Dish> fetchDishs() {
        try {
            return SQLiteWrapper.execute("SELECT * FROM tableDish").stream()
                    .map(row -> {
                        String name = (String) row[1];
                        String description = (String) row[2];
                        int price = (Integer) row[3];
                        String imgPath = (String) row[4];

                        Image image = null;
                        if (imgPath != null && !imgPath.isEmpty()) {
                            try {
                                File imageFile = new File("assets/" + new File(imgPath).getName());
                                if (imageFile.exists()) {
                                    image = new Image(imageFile.toURI().toString(), true);
                                } else {
                                    System.err.println("L'image n'a pas été trouvée dans le dossier assets : '" + imageFile.getPath() + "'");
                                }
                            } catch (Exception e) {
                                System.err.println("Exception lors du chargement de l'image : " + e.getMessage());
                            }
                        }

                        Dish dish = new Dish(name, description, price, image);
                        dish.setImagePath(imgPath);
                        return dish;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des plats depuis la base de données : " + e.getMessage());
            return List.of();
        }
    }

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

    public static void removeDish(Dish dish) {
        try {
            SQLiteWrapper.execute("DELETE FROM tableDish WHERE name = ?", dish.name);
            dishs.remove(dish);
        } catch (Exception e) {
            System.err.println("Échec de la suppression du plat : " + e.getMessage());
        }
    }
}
