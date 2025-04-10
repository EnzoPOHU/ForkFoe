module com.forkfoe.forkfoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.forkfoe.forkfoe to javafx.fxml;
    exports com.forkfoe.forkfoe;
    exports com.forkfoe.forkfoe.controller.table;
    opens com.forkfoe.forkfoe.controller.table to javafx.fxml;
    exports com.forkfoe.forkfoe.repository;
    opens com.forkfoe.forkfoe.repository to javafx.fxml;
}