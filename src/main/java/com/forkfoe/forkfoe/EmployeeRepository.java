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
                    .map(row -> new Employee((String) row[1], (String) row[2], (Integer) row[3]))
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
                    employee.name, employee.role, employee.workedTime);
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
            SQLiteWrapper.execute("DELETE FROM employee WHERE name = ?", employee.name);
            employees.remove(employee);
        } catch (Exception e) {
            System.err.println("Failed to remove an employee: " + e.getMessage());
        }
    }
}
