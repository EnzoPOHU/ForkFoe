package com.forkfoe.forkfoe;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.*;

public class AddDishController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField priceField;
    @FXML private TextField imagePathField;

    private File imageFile;

    /**
     * Select an image in your computer
     */
    @FXML
    public void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        imageFile = fileChooser.showOpenDialog(null);

        if (imageFile != null) {
            imagePathField.setText(imageFile.getAbsolutePath());
        }
    }

    /**
     * Push in database new dish
     */
    @FXML
    public void submitDish() {
        try {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            int price = Integer.parseInt(priceField.getText().trim());

            if (name.isEmpty() || description.isEmpty() || price < 0) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis correctement.");
            }

            if (imageFile != null) {
                File destDir = new File("data");
                if (!destDir.exists()) destDir.mkdirs();

                File destFile = new File(destDir, imageFile.getName());
                Files.copy(imageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Dish newDish = new Dish(name, description, price, destFile.getName());

                DishRepository.addDish(newDish);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Plat ajouté avec succès !");
                alert.showAndWait();

                ((Stage) nameField.getScene().getWindow()).close();
            } else {
                throw new IllegalArgumentException("Veuillez choisir une image.");
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du plat : " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Quit popup new dish
     */
    @FXML
    public void cancel() {
        ((Stage) nameField.getScene().getWindow()).close();
    }
}
