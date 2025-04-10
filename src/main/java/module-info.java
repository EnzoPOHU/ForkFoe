module com.forkfoe.forkfoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.forkfoe.forkfoe to javafx.fxml;
    exports com.forkfoe.forkfoe;
    exports com.forkfoe.forkfoe.repository;
    opens com.forkfoe.forkfoe.repository to javafx.fxml;
    exports com.forkfoe.forkfoe.model;
    opens com.forkfoe.forkfoe.model to javafx.fxml;
    exports com.forkfoe.forkfoe.controller;
    opens com.forkfoe.forkfoe.controller to javafx.fxml;
    exports com.forkfoe.forkfoe.util;
    opens com.forkfoe.forkfoe.util to javafx.fxml;
}