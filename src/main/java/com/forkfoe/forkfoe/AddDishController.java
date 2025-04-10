package com.forkfoe.forkfoe;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AddDishController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField priceField;
    @FXML private TextField imagePathField;

    private File imageFile;

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

                // Création du plat en passant le chemin de l'image (pas l'objet Image)
                Dish newDish = new Dish(name, description, price, destFile.getName());

                // Ajouter le plat dans la base de données
                DishRepository.addDish(newDish);

                // Message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Plat ajouté avec succès !");
                alert.showAndWait();

                // Fermer la fenêtre après l'ajout
                ((Stage) nameField.getScene().getWindow()).close();
            } else {
                throw new IllegalArgumentException("Veuillez choisir une image.");
            }

        } catch (Exception e) {
            // Gestion des erreurs
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du plat : " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel() {
        ((Stage) nameField.getScene().getWindow()).close();
    }
}
