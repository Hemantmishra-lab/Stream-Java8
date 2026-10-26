package com.example.streams.hard;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Helper Enums & DTOs
enum SalaryRange { LOW, MEDIUM, HIGH }
enum CharCategory { UPPERCASE, LOWERCASE, DIGIT, OTHER }

class Employee {
    private final String name;
    private final double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
}

class Transaction {
    private final LocalDate date;
    private final double amount;

    public Transaction(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
}

class WorkRecord {
    private final String employeeName;
    private final String department;

    public WorkRecord(String employeeName, String department) {
        this.employeeName = employeeName;
        this.department = department;
    }

    public String getEmployeeName() { return employeeName; }
    public String getDepartment() { return department; }
}

public class HardStreamPractice {

    public static void main(String[] args) {

        // 1. Most Common First Letter Among Employee Names
        System.out.println("--- 1. MOST COMMON FIRST LETTER ---");
        List<Employee> employees = List.of(
                new Employee("Alice", 60000),
                new Employee("Andrew", 75000),
                new Employee("Bob", 80000),
                new Employee("Charlie", 90000),
                new Employee("Amanda", 50000)
        );

        Character mostCommonFirstLetter = employees.stream()
                .map(e -> e.getName().charAt(0))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(' ');
        System.out.println("Most common first letter: " + mostCommonFirstLetter);

        // 2. Sliding Window Average (Size = 3)
        System.out.println("\n--- 2. SLIDING WINDOW AVERAGE ---");
        List<Integer> windowData = List.of(10, 20, 30, 40, 50, 60);
        int windowSize = 3;

        List<Double> windowAverages = IntStream.range(0, windowData.size() - windowSize + 1)
                .mapToObj(i -> windowData.subList(i, i + windowSize))
                .mapToDouble(sub -> sub.stream().mapToInt(Integer::intValue).average().orElse(0.0))
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Sliding window averages: " + windowAverages);

        // 3. Longest Word in a Sentence (Ignoring Punctuation)
        System.out.println("\n--- 3. LONGEST WORD IN A SENTENCE ---");
        String sentence = "Java Stream API, providing powerful functional-style capabilities!";
        String longestWord = Arrays.stream(sentence.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        System.out.println("Longest word: " + longestWord);

        // 4. Top 3 Most Frequent Words
        System.out.println("\n--- 4. TOP 3 MOST FREQUENT WORDS ---");
        String paragraph = "Java is powerful. Java is versatile. Streams in Java are fast and functional.";
        List<String> top3Words = Arrays.stream(paragraph.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("Top 3 words: " + top3Words);

        // 5. Reverse Each Word in a Sentence
        System.out.println("\n--- 5. REVERSE EACH WORD ---");
        String textToReverse = "Java Streams Are Awesome";
        String reversedSentence = Arrays.stream(textToReverse.split("\\s+"))
                .map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.joining(" "));
        System.out.println("Reversed: " + reversedSentence);

        // 6. Day with Highest Total Transaction Spend
        System.out.println("\n--- 6. HIGHEST SPEND DAY ---");
        List<Transaction> transactions = List.of(
                new Transaction(LocalDate.of(2026, 1, 1), 200.0),
                new Transaction(LocalDate.of(2026, 1, 1), 300.0),
                new Transaction(LocalDate.of(2026, 1, 2), 700.0),
                new Transaction(LocalDate.of(2026, 1, 2), 150.0),
                new Transaction(LocalDate.of(2026, 1, 3), 400.0)
        );

        Map.Entry<LocalDate, Double> maxSpendDay = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getDate, Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.println("Highest Spend Day: " + maxSpendDay.getKey() + " ($" + maxSpendDay.getValue() + ")");

        // 7. Categorize Employees by Salary Range
        System.out.println("\n--- 7. CATEGORIZE BY SALARY RANGE ---");
        Map<SalaryRange, List<Employee>> categorizedEmployees = employees.stream()
                .collect(Collectors.groupingBy(e -> {
                    if (e.getSalary() < 60000) return SalaryRange.LOW;
                    if (e.getSalary() <= 80000) return SalaryRange.MEDIUM;
                    return SalaryRange.HIGH;
                }));
        categorizedEmployees.forEach((range, empList) ->
                System.out.println(range + ": " + empList.stream().map(Employee::getName).collect(Collectors.toList())));

        // 8. Categorize Characters
        System.out.println("\n--- 8. CATEGORIZE CHARACTERS ---");
        String mixedStr = "aB1! cD2#";
        Map<CharCategory, List<Character>> categorizedChars = mixedStr.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !Character.isWhitespace(c))
                .collect(Collectors.groupingBy(c -> {
                    if (Character.isUpperCase(c)) return CharCategory.UPPERCASE;
                    if (Character.isLowerCase(c)) return CharCategory.LOWERCASE;
                    if (Character.isDigit(c)) return CharCategory.DIGIT;
                    return CharCategory.OTHER;
                }));
        System.out.println("Categorized Chars: " + categorizedChars);

        // 9. Employees Who Worked in 3+ Departments
        System.out.println("\n--- 9. EMPLOYEES WORKED IN 3+ DEPTS ---");
        List<WorkRecord> records = List.of(
                new WorkRecord("John", "IT"),
                new WorkRecord("John", "Finance"),
                new WorkRecord("John", "HR"),
                new WorkRecord("Alice", "IT"),
                new WorkRecord("Alice", "HR"),
                new WorkRecord("Bob", "Finance")
        );

        List<String> multiDeptEmployees = records.stream()
                .collect(Collectors.groupingBy(WorkRecord::getEmployeeName, Collectors.mapping(WorkRecord::getDepartment, Collectors.toSet())))
                .entrySet().stream()
                .filter(e -> e.getValue().size() >= 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("Employees in 3+ depts: " + multiDeptEmployees);

        // 10. Bigram Frequencies in a Paragraph
        System.out.println("\n--- 10. BIGRAM FREQUENCIES ---");
        String bigramText = "Java is great and Java is fun and Java is powerful";
        String[] words = bigramText.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");

        Map<String, Long> bigramCounts = IntStream.range(0, words.length - 1)
                .mapToObj(i -> words[i] + " " + words[i + 1])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Bigram frequencies: " + bigramCounts);
    }
}