package com.forkfoe.forkfoe.controller;

import com.forkfoe.forkfoe.model.Dish;
import com.forkfoe.forkfoe.repository.DishRepository;
import com.forkfoe.forkfoe.model.TableOrder;
import com.forkfoe.forkfoe.repository.TableOrderRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishController {

    @FXML
    private VBox dishContainer;

    private final Map<CheckBox, Spinner<Integer>> dishMap = new HashMap<>();

    private final List<Dish> dishes = DishRepository.getDish();
    @FXML
    private TextField ingredientSearch;


    public Label totalLabel;

    @FXML
    public void initialize() {
        List<Dish> dishes = DishRepository.fetchDishs()
                .stream()
                .sorted((d1, d2) -> Integer.compare(d2.getPrice(), d1.getPrice()))
                .toList();

        dishes.forEach(this::addDishToView);

        totalCard();
        refreshDishList();
    }


    @FXML
    private void onIngredientSearch() {
        String query = ingredientSearch.getText();
        filterDishesByIngredient(query);
    }




    private void filterDishesByIngredient(String ingredient) {
        dishContainer.getChildren().clear();
        String lowerIngredient = ingredient.toLowerCase();
        dishes.stream().filter(dish -> dish.description.toLowerCase().contains(lowerIngredient)).forEach(this::addDishToView);
    }


    @FXML
    private void validCommand() {
        StringBuilder commande = new StringBuilder("Commande envoyée :\n");
        int bill = 0;
        for (Map.Entry<CheckBox, Spinner<Integer>> entry : dishMap.entrySet()) {
            CheckBox cb = entry.getKey();
            Spinner<Integer> spinner = entry.getValue();

            if (cb.isSelected() && spinner.getValue() > 0) {
                int quantity = spinner.getValue();
                String dishName = cb.getText();

                Dish selectedDish = dishes.stream()
                        .filter(d -> d.name.equals(dishName))
                        .findFirst()
                        .orElse(null);

                if (selectedDish != null) {
                    int price = selectedDish.getPrice();
                    bill += price * quantity;
commande.append("- " + dishName + " x" + quantity +" (" + price + "€)" + "\n");
                }
            }
        }

        if (commande.toString().equals("Commande envoyée :\n")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun plat sélectionné.");
            alert.showAndWait();
            return;
        }

        int table = 1;
        onCreateOrderClick(bill, table);
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Commande validée !");
        confirmation.showAndWait();
    }

    /**
     * Open popup add new dish
     */
    @FXML
    private void openAddDishPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/AddDishForm.fxml"));
            VBox content = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Ajouter un Plat");
            dialog.getDialogPane().setContent(content);

            dialog.showAndWait();

            refreshDishList();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Refresh main page to show new dish
     */
    @FXML
    public void refreshDishList() {
        dishContainer.getChildren().clear();

        List<Dish> updatedDishes = DishRepository.getDish()
                .stream()
                .sorted((d1, d2) -> Integer.compare(d2.getPrice(), d1.getPrice()))
                .toList();

        updatedDishes.forEach(this::addDishToView);

        totalCard();
    }

    /**
     * Popup with all input to create new dish
     */
    private void addDishToView(Dish dish) {
        CheckBox checkBox = new CheckBox(dish.name);
        Button viewButton = new Button("Détail");
        Button delButton = new Button("Supprimer");
        delButton.getStyleClass().add("button-delete");
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinner.setDisable(true);

        checkBox.setOnAction(_ -> spinner.setDisable(!checkBox.isSelected()));

        viewButton.setOnAction(_ -> openDetailPopup(dish));

        delButton.setOnAction(_ -> {

            DishRepository.removeDish(dish);

            dishContainer.getChildren().removeIf(node -> ((HBox) node).getChildren().contains(delButton));
            totalCard();

        });

        totalCard();


        HBox dishRow = new HBox(10, checkBox, spinner, viewButton, delButton);
        dishContainer.getChildren().add(dishRow);

        dishMap.put(checkBox, spinner);
    }

    /**
     * Popup to show detail of one dish
     */
    private void openDetailPopup(Dish dish) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/DetailDishForm.fxml"));
            DialogPane pane = loader.load();

            DetailDishController controller = loader.getController();
            controller.setDish(dish);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Détails du plat");
            dialog.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'affichage des détails : " + e.getMessage());
            alert.showAndWait();
        }
    }


    private void onCreateOrderClick(int bill, int tableId) {
        try {
            String status = "En cours";
            TableOrder newOrder = new TableOrder(bill, status, tableId);
            TableOrderRepository.addOrder(newOrder);


        } catch (NumberFormatException e) {
            System.err.println("Erreur : Veuillez entrer des valeurs valides.");
        }
    }

    @FXML

    private void totalCard() {
        double total = DishRepository.getDish()
                .stream()
                .mapToDouble(Dish::getPrice)
                .sum();

        totalLabel.setText("Le total de la carte est de : " + String.format("%.2f", total) + " €");
    }

}
