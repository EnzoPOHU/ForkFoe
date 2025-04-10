package com.forkfoe.forkfoe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Employee {
    private IntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty role;
    private SimpleIntegerProperty workingTime;

    public Employee(String name, String role, Integer workingTime) {
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleStringProperty(role);
        this.workingTime = new SimpleIntegerProperty(workingTime);
    }

    public Employee(Integer id, String name, String role, Integer workingTime) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleStringProperty(role);
        this.workingTime = new SimpleIntegerProperty(workingTime);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
       this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public Integer getWorkingTime() {
        return workingTime.get();
    }

    public void setWorkingTime(Integer workingTime) {
        this.workingTime.set(workingTime);
    }

    public SimpleIntegerProperty workingTimeProperty() {
        return workingTime;
    }
}
