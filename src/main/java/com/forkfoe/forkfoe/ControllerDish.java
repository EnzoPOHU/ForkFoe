package com.forkfoe.forkfoe;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ControllerDish {

    @FXML private CheckBox checkboxPizza;
    @FXML private Spinner<Integer> spinnerPizza;

    @FXML private CheckBox checkboxBurger;
    @FXML private Spinner<Integer> spinnerBurger;

    @FXML
    public void initialize() {
        spinnerPizza.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
    }

    @FXML
    private void validerCommande() {
        StringBuilder commande = new StringBuilder("Commande :\n");

        if (checkboxPizza.isSelected()) {
            commande.append("Pizza x").append(spinnerPizza.getValue()).append("\n");
        }
        if (checkboxBurger.isSelected()) {
            commande.append("Burger x").append(spinnerBurger.getValue()).append("\n");
        }

        System.out.println(commande.toString());
    }
}
