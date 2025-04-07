module com.forkfoe.forkfoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.forkfoe.forkfoe to javafx.fxml;
    exports com.forkfoe.forkfoe;
}