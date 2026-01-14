package miniproject2;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeService service = new EmployeeService();

    public static void main(String[] args) {
        if (!login()) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> displayAllEmployees();
                    case 3 -> searchEmployee();
                    case 4 -> updateSalary();
                    case 5 -> deleteEmployee();
                    case 6 -> displaySortedEmployees();
                    case 7 -> displayDepartments();
                    case 8 -> {
                        System.out.println("Exiting...");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static boolean login() {
        System.out.println("\n=== LOGIN SYSTEM ===");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        // Hardcoded for demonstration, as per plan
        return "admin".equals(user) && "admin123".equals(pass);
    }

    private static void printMenu() {
        System.out.println("\n---------------------------------");
        System.out.println("   Employee Management System");
        System.out.println("---------------------------------");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Update Employee Salary");
        System.out.println("5. Delete Employee");
        System.out.println("6. Display Sorted Employees (by ID)");
        System.out.println("7. Display Departments");
        System.out.println("8. Exit");
        System.out.println("---------------------------------");
    }

    private static void addEmployee() {
        try {
            int id = getIntInput("Enter Employee ID: ");
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            Double salary = getDoubleInput("Enter Salary: ");
            System.out.print("Enter Department: ");
            String dept = scanner.nextLine();

            Employee emp = new Employee(id, name, salary, dept);
            service.addEmployee(emp);
            System.out.println("Employee added successfully!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private static void displayAllEmployees() {
        List<Employee> list = service.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\n--- All Employees ---");
            list.forEach(System.out::println);
        }
    }

    private static void searchEmployee() {
        int id = getIntInput("Enter Employee ID to search: ");
        Employee emp = service.searchEmployeeById(id);
        if (emp != null) {
            System.out.println("Found: " + emp);
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void updateSalary() {
        int id = getIntInput("Enter Employee ID to update: ");
        Double salary = getDoubleInput("Enter new Salary: ");
        try {
            service.updateEmployeeSalary(id, salary);
            System.out.println("Salary updated successfully!");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        int id = getIntInput("Enter Employee ID to delete: ");
        try {
            service.deleteEmployee(id);
            System.out.println("Employee deleted successfully.");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displaySortedEmployees() {
        List<Employee> list = service.getSortedEmployees();
        if (list.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\n--- Sorted Employees (by ID) ---");
            list.forEach(System.out::println);
        }
    }

    private static void displayDepartments() {
        Set<String> depts = service.getUniqueDepartments();
        if (depts.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            System.out.println("\n--- Departments ---");
            depts.forEach(System.out::println);
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static Double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}