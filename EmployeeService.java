package miniproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

public class EmployeeService {
    private List<Employee> employees;

    public EmployeeService() {
        this.employees = FileHandler.readEmployees();
    }

    public void addEmployee(Employee emp) throws ValidationException {
        if (searchEmployeeById(emp.getId()) != null) {
            throw new ValidationException("Employee with ID " + emp.getId() + " already exists.");
        }
        employees.add(emp);
        save();
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Employee searchEmployeeById(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void updateEmployeeSalary(int id, double newSalary) throws ValidationException {
        Employee emp = searchEmployeeById(id);
        if (emp == null) {
            throw new ValidationException("Employee not found.");
        }
        emp.setSalary(newSalary);
        save();
    }

    public void deleteEmployee(int id) throws ValidationException {
        Employee emp = searchEmployeeById(id);
        if (emp == null) {
            throw new ValidationException("Employee not found.");
        }
        employees.remove(emp);
        save();
    }

    public List<Employee> getSortedEmployees() {
        // Sorts by Natural Order (ID) as defined in Employee data class
        return employees.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<String> getUniqueDepartments() {
        return employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toSet());
    }

    private void save() {
        FileHandler.writeEmployees(employees);
    }
}
