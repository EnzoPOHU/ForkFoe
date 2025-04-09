package com.forkfoe.forkfoe.controller.table;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTableController {

    @FXML
    private TextField tableNumberInput;

    @FXML
    private TextField maxSeatsInput;

    // Action déclenchée lorsque l'utilisateur clique sur "Enregistrer"
    @FXML
    public void onSaveTableClicked() {
        // Récupérer les valeurs saisies
        String tableNumber = tableNumberInput.getText();
        String maxSeats = maxSeatsInput.getText();

        // Vérifier les entrées (validation simplifiée ici, mais on peut l'adapter)
        if (tableNumber.isEmpty() || maxSeats.isEmpty()) {
            System.err.println("Tous les champs doivent être remplis !");
            return;
        }

        // Afficher les valeurs récupérées, ou les envoyer pour un traitement
        System.out.println("Table créée : Numéro = " + tableNumber + ", Nombre de places = " + maxSeats);

        // Fermer la fenêtre courante
        closeWindow();
    }

    // Action déclenchée lorsque l'utilisateur clique sur "Annuler"
    @FXML
    public void onCancelClicked() {
        closeWindow();
    }

    // Méthode utilitaire pour fermer la fenêtre courante (le formulaire)
    private void closeWindow() {
        Stage stage = (Stage) tableNumberInput.getScene().getWindow();
        stage.close();
    }
}