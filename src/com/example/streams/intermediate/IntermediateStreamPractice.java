package com.example.streams.intermediate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class Employee {
    private final int id;
    private final String name;
    private final String department;
    private final double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', dept='%s', salary=%.2f}", id, name, department, salary);
    }
}

class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}

public class IntermediateStreamPractice {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(101, "Alice", "HR", 55000),
                new Employee(102, "Bob", "IT", 85000),
                new Employee(103, "Charlie", "IT", 95000),
                new Employee(104, "Diana", "Finance", 65000),
                new Employee(105, "Ethan", "HR", 50000)
        );

        // 1. Sort Employees by Salary
        System.out.println("--- 1. SORT EMPLOYEES BY SALARY ---");
        List<Employee> sortedBySalary = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());
        sortedBySalary.forEach(System.out::println);

        // 2. Average Age of Persons
        System.out.println("\n--- 2. AVERAGE AGE OF PERSONS ---");
        List<Person> persons = List.of(
                new Person("John", 28),
                new Person("Jane", 34),
                new Person("Alex", 22)
        );
        double avgAge = persons.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0.0);
        System.out.println("Average Age: " + avgAge);

        // 3. Partition Even and Odd Numbers
        System.out.println("\n--- 3. PARTITION EVEN AND ODD ---");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, List<Integer>> evenOddMap = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Even: " + evenOddMap.get(true));
        System.out.println("Odd: " + evenOddMap.get(false));

        // 4. Group Words by Length
        System.out.println("\n--- 4. GROUP WORDS BY LENGTH ---");
        List<String> words = List.of("apple", "bat", "ball", "cat", "banana", "dog");
        Map<Integer, List<String>> wordsByLength = words.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + wordsByLength);

        // 5. Count Word Occurrences
        System.out.println("\n--- 5. COUNT WORD OCCURRENCES ---");
        List<String> fruits = List.of("apple", "banana", "apple", "orange", "banana", "apple");
        Map<String, Long> fruitCounts = fruits.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Counts: " + fruitCounts);

        // 6. Average Salary per Department
        System.out.println("\n--- 6. AVERAGE SALARY PER DEPARTMENT ---");
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));
        System.out.println("Avg Salary: " + avgSalaryByDept);

        // 7. Highest Paid Employee in Each Department
        System.out.println("\n--- 7. HIGHEST PAID IN EACH DEPT ---");
        Map<String, Optional<Employee>> highestPaidByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
                ));
        highestPaidByDept.forEach((dept, emp) ->
                System.out.println(dept + ": " + emp.map(Employee::getName).orElse("None")));

        // 8. Departments with More Than N Employees
        System.out.println("\n--- 8. DEPTS WITH > 1 EMPLOYEE ---");
        List<String> largeDepts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("Large Departments: " + largeDepts);

        // 9. Department with Highest Average Salary
        System.out.println("\n--- 9. DEPT WITH HIGHEST AVERAGE SALARY ---");
        Map.Entry<String, Double> topDept = avgSalaryByDept.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.println("Top Dept: " + topDept.getKey() + " ($" + topDept.getValue() + ")");

        // 10. Most Frequent Character in a String
        System.out.println("\n--- 10. MOST FREQUENT CHARACTER ---");
        String input = "banana";
        Map.Entry<Character, Long> mostFrequentChar = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.println("Most frequent char in '" + input + "': " + mostFrequentChar.getKey());

        // 11. First Non-Repeating Character
        System.out.println("\n--- 11. FIRST NON-REPEATING CHARACTER ---");
        String text = "swiss";
        Optional<Character> firstNonRepeating = text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new, // Preserves insertion order
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();
        System.out.println("First non-repeating in '" + text + "': " + firstNonRepeating.orElse(' '));
    }
}