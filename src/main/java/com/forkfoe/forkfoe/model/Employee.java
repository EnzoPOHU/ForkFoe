package com.forkfoe.forkfoe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Employee {
    private IntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty age;
    private SimpleStringProperty role;
    private SimpleIntegerProperty workingTime;

    public Employee(String name,Integer age , String role, Integer workingTime) {
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.role = new SimpleStringProperty(role);
        this.workingTime = new SimpleIntegerProperty(workingTime);
    }

    public Employee(Integer id, String name, Integer age, String role, Integer workingTime) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
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

    public String getRole() {
        return role.get();
    }

    public Integer getWorkingTime() {
        return workingTime.get();
    }

    public int getAge() {
        return age.get();
    }
}
