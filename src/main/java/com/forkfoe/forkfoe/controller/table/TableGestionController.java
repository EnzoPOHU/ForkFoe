package com.forkfoe.forkfoe.controller.table;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TableGestionController {

    @FXML
    public void onAddTableButtonClicked() {
        try {
            // Chemin corrigé vers le dossier "table"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/table/AddTableForm.fxml"));

            // Charger le fichier FXML
            System.out.println("Chargement du fichier FXML depuis : " + getClass().getResource("/com/forkfoe/forkfoe/fxml/table/AddTableForm.fxml"));

            // Créer et afficher la fenêtre
            Stage formStage = new Stage();
            Scene formScene = new Scene(loader.load());
            formStage.setTitle("Créer une table");
            formStage.setScene(formScene);
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du formulaire de création de table.");
        }
    }
}