package miniproject2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "employees.txt";

    public static List<Employee> readEmployees() {
        List<Employee> employees = new ArrayList<>();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            return employees;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Employee emp = Employee.fromCSV(line);
                if (emp != null) {
                    employees.add(emp);
                }
            }
        } catch (IOException | ValidationException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return employees;
    }

    public static void writeEmployees(List<Employee> employees) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                bw.write(emp.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
