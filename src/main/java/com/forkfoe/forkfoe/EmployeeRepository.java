package com.forkfoe.forkfoe;
import java.util.*;
import java.util.stream.*;

public class EmployeeRepository {
    static { new EmployeeRepository(); }

    private static List<Employee> employees;

    private EmployeeRepository() {
        employees = fetchEmployees();
    }

    /**
     * Get in-memory employees list
     * @return employees
     */
    public static List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Fetch employees from the database to this repository
     * @return employees All the employees
     */
    public static List<Employee> fetchEmployees() {
        try {
            employees = SQLiteWrapper.execute("SELECT * FROM employee").stream()
                    .map(row -> new Employee((Integer) row[0], (String) row[1], (String) row[2], (Integer) row[3]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Failed to fetch employees from database: " + e.getMessage());
        }
        return employees;
    }

    /**
     * Create an employee
     * @param employee E.g: an intern
     */
    public static void addEmployee(Employee employee) {
        try {
            SQLiteWrapper.execute("INSERT INTO employee (name, role, worked_time) VALUES (?,?,?)",
                    employee.getName(), employee.getRole(), employee.getWorkingTime());
            employee.setId((Integer)SQLiteWrapper.execute("SELECT id FROM employee ORDER BY id DESC LIMIT 1").getFirst()[0]);
            employees.add(employee);
        } catch (Exception e) {
            System.err.println("Failed to add an employee: " + e.getMessage());
        }
    }

    /**
     * Delete a staff
     * @param employee E.g: a fired one
     */
    public static void removeEmployee(Employee employee) {
        try {
            if (!employees.removeIf(e -> e.getId().equals(employee.getId()))) {
                throw new Exception("Couldn't find " + employee.getName());
            }
            SQLiteWrapper.execute("DELETE FROM employee WHERE id = ?", employee.getId());
        } catch (Exception e) {
            System.err.println("Failed to remove an employee: " + e.getMessage());
        }
    }
}
