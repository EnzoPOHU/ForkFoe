package com.forkfoe.forkfoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetailDishController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ImageView imageView;

    @FXML
    private Button backButton;

    /*
    show in detail the dish
     */
    public void setDish(Dish dish) {
        nameLabel.setText(dish.name);
        priceLabel.setText(dish.getPrice() + " €");
        descriptionArea.setText(dish.description);

        if (dish.img != null && !dish.img.isError()) {
            imageView.setImage(new Image(dish.img.getUrl()));
        }
    }

    /*
    quit popup detail of dish
     */
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
