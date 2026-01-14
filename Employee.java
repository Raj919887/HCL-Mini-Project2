package miniproject2;

import java.io.Serializable;

public class Employee implements Serializable, Comparable<Employee> {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private double salary;
    private String department;

    public Employee(int id, String name, double salary, String department) throws ValidationException {
        this.id = id;
        this.name = name;
        setSalary(salary);
        setDepartment(department);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) throws ValidationException {
        if (salary <= 0) {
            throw new ValidationException("Salary must be positive.");
        }
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) throws ValidationException {
        if (department == null || department.trim().isEmpty()) {
            throw new ValidationException("Department cannot be empty.");
        }
        this.department = department;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s | Salary: %-10.2f | Dept: %s", 
                id, name, salary, department);
    }

    public String toCSV() {
        return id + "," + name + "," + salary + "," + department;
    }

    public static Employee fromCSV(String csvLine) throws ValidationException {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length < 4) return null;
            
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            double salary = Double.parseDouble(parts[2].trim());
            String dept = parts[3].trim();
            
            return new Employee(id, name, salary, dept);
        } catch (NumberFormatException e) {
            // Skip bad lines or handle explicitly
            return null;
        }
    }

    @Override
    public int compareTo(Employee o) {
        return Integer.compare(this.id, o.id);
    }
}
