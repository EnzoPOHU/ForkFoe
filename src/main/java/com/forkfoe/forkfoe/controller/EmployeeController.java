package com.forkfoe.forkfoe.controller;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.EmployeeRepository;
import com.forkfoe.forkfoe.model.Employee;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeController {
    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, String> employeesName;

    @FXML
    private TableColumn<Employee, String> employeesRole;

    @FXML
    private TableColumn<Employee, String> employeesWorkingTime;

    @FXML
    private TableColumn<Employee, String> employeesAge;

    @FXML
    private TextField newEmployeeName;

    @FXML
    private ComboBox<String> newEmployeeRole;

    @FXML
    private TextField newEmployeeWorkingTime;

    @FXML
    private TextField newEmployeeAge;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button removeEmployeeButton;

    private ObservableList<Employee> employeesList;

    @FXML
    public void initialize() {
        employeesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeesRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        employeesWorkingTime.setCellValueFactory(new PropertyValueFactory<>("workingTime"));
        employeesAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        employeesList = FXCollections.observableArrayList();
        var fetchedEmployees = EmployeeRepository.getEmployees();

        if (fetchedEmployees != null) {
            employeesList.addAll(fetchedEmployees);
        } else {
            System.err.println("Erreur: La liste des employés est vide ou nulle.");
        }

        employeesTable.setItems(employeesList);

        employeesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removeEmployeeButton.setDisable(newValue == null);
        });
    }


    @FXML
    protected void onAddEmployee() {
        String name = newEmployeeName.getText();
        String role = newEmployeeRole.getValue();
        String workingTime = newEmployeeWorkingTime.getText();
        String ageText = newEmployeeAge.getText();

        try {
            int workingTimeInt = Integer.parseInt(workingTime);
            int age = Integer.parseInt(ageText);
            Employee newEmployee = new Employee(name, age, role, workingTimeInt);
            employeesList.add(newEmployee);
            EmployeeRepository.addEmployee(newEmployee);
            newEmployeeName.clear();
            newEmployeeWorkingTime.clear();
            newEmployeeAge.clear();
            employeesTable.refresh();
            employeesTable.requestFocus();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'un employé : " + e.getMessage());
        }
    }

    @FXML
    protected void onRemoveEmployee() {
        int selectedEmployeeIndex = employeesTable.getSelectionModel().getSelectedIndex();
        if (selectedEmployeeIndex >= 0) {
            Employee selectedEmployee = employeesList.get(selectedEmployeeIndex);
            employeesList.remove(selectedEmployee);
            EmployeeRepository.removeEmployee(selectedEmployee);
            employeesTable.refresh();
        }
    }

    private void validateAddEmployeeForm() {
        addEmployeeButton.setDisable(
                newEmployeeName.getText().isBlank()
                        || newEmployeeRole.getValue() == null
                        || newEmployeeWorkingTime.getText().isBlank()
                        || newEmployeeAge.getText().isBlank()
        );
    }

    @FXML protected void onTypingEmployeeName() { validateAddEmployeeForm(); }

    @FXML protected void onSettingEmployeeRole() { validateAddEmployeeForm(); }

    @FXML protected void onTypingEmployeeWorkingTime() {
        try {
            int workingTime = Integer.parseInt(newEmployeeWorkingTime.getText());
            if (workingTime > 0 && workingTime < 24) {
                validateAddEmployeeForm();
            } else newEmployeeWorkingTime.clear();
        } catch (NumberFormatException e) {
            newEmployeeWorkingTime.clear();
        }
    }

    @FXML protected void onTypingEmployeeAge() {
        try {
            int age = Integer.parseInt(newEmployeeAge.getText());
            if (age > 0 && age < 120) {
                validateAddEmployeeForm();
            } else newEmployeeAge.clear();
        } catch (NumberFormatException e) {
            newEmployeeAge.clear();
        }
    }

    @FXML
    protected void onFilterUnder30() {
        employeesTable.setItems(employeesList.filtered(e -> e.getAge() < 30));
    }

    @FXML
    protected void onFilterBetween45And60() {
        employeesTable.setItems(employeesList.filtered(e -> e.getAge() >= 45 && e.getAge() <= 60));
    }

    @FXML
    protected void onFilterOver60() {
        employeesTable.setItems(employeesList.filtered(e -> e.getAge() > 60));
    }

    @FXML
    protected void onShowAll() {
        employeesTable.setItems(employeesList);
    }
}
