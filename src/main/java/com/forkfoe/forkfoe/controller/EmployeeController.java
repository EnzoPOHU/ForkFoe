package com.forkfoe.forkfoe.controller;

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
    private TextField newEmployeeName;

    @FXML
    private ComboBox<String> newEmployeeRole;

    @FXML
    private TextField newEmployeeWorkingTime;

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
        employeesList = FXCollections.observableArrayList();
        employeesList.addAll(EmployeeRepository.getEmployees());
        employeesTable.setItems(employeesList);
        employeesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removeEmployeeButton.setDisable(newValue == null);
        });
    }

    /**
     * Effect of the add employee button
     */
    @FXML
    protected void onAddEmployee() {
        String name = newEmployeeName.getText();
        String role = newEmployeeRole.getValue().toString();
        String workingTime = newEmployeeWorkingTime.getText();
        try {
            Employee newEmployee = new Employee(name, role, Integer.parseInt(workingTime));
            employeesList.add(newEmployee);
            EmployeeRepository.addEmployee(newEmployee);
            newEmployeeName.clear();
            newEmployeeWorkingTime.clear();
            employeesTable.refresh();
            employeesTable.requestFocus();
        } catch (Exception _) {
            System.err.println("Failed to add a valid employee");
        }
    }

    /**
     * Effect of the remove employee button
     */
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

    /**
     * Update the add employee button according to the last form validation
     */
    private void validateAddEmployeeForm() {
        addEmployeeButton.setDisable(newEmployeeName.getText().isBlank()
                || newEmployeeRole.getValue() == null
                || newEmployeeWorkingTime.getText().isBlank());
    }

    /**
     * Set employee name and validate the form fields
     */
    @FXML protected void onTypingEmployeeName() { validateAddEmployeeForm(); }

    /**
     * Set employee role and validate the form fields
     */
    @FXML protected void onSettingEmployeeRole() { validateAddEmployeeForm(); }

    /**
     * This text field only accept numbers to set employee working time
     */
    @FXML
    protected void onTypingEmployeeWorkingTime() {
        try {
            int workingTime = Integer.parseInt(newEmployeeWorkingTime.getText());
            if (workingTime > 0 && workingTime < 24) {
                validateAddEmployeeForm();
            } else newEmployeeWorkingTime.clear();
        } catch (NumberFormatException e) {
            newEmployeeWorkingTime.clear();
        }
    }
}
