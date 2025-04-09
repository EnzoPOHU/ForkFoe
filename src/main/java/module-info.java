module com.forkfoe.forkfoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.forkfoe.forkfoe to javafx.fxml;
    exports com.forkfoe.forkfoe;
    exports com.forkfoe.forkfoe.controller.table;
    opens com.forkfoe.forkfoe.controller.table to javafx.fxml;
}