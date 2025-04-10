module com.forkfoe.forkfoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.forkfoe.forkfoe to javafx.fxml;
    exports com.forkfoe.forkfoe;
}