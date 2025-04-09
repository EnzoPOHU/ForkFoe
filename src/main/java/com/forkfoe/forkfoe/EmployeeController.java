package com.forkfoe.forkfoe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class EmployeeController {
    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, String> employeesName;

    @FXML
    private TableColumn<Employee, String> employeesRole;

    @FXML
    private TableColumn<Employee, String> employeesWorkingTime;

    private ObservableList<Employee> employeesList;

    @FXML
    public void initialize() {
        employeesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeesRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        employeesWorkingTime.setCellValueFactory(new PropertyValueFactory<>("workingTime"));

        employeesList = FXCollections.observableArrayList();
        employeesList.addAll(EmployeeRepository.getEmployees());
        employeesTable.setItems(employeesList);
    }
}
