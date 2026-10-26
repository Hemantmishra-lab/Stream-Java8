package com.example.streams.supereasy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SuperEasyStreamPractice {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        // 1. Filter Even Numbers
        System.out.println("--- 1. FILTER EVEN NUMBERS ---");
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Even Numbers: " + evenNumbers);

        // 2. Convert Names to Uppercase (Mapping)
        System.out.println("\n--- 2. MAP TO UPPERCASE ---");
        List<String> uppercaseNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Uppercase Names: " + uppercaseNames);

        // 3. Count Elements Matching Condition
        System.out.println("\n--- 3. COUNT MATCHING ELEMENTS ---");
        long countGreaterThanFive = numbers.stream()
                .filter(n -> n > 5)
                .count();
        System.out.println("Count > 5: " + countGreaterThanFive);

        // 4. Find Sum using reduce()
        System.out.println("\n--- 4. REDUCE SUM ---");
        int totalSum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Total Sum: " + totalSum);

        // 5. Find First Element
        System.out.println("\n--- 5. FIND FIRST MATCH ---");
        Optional<String> firstNameStartingWithD = names.stream()
                .filter(name -> name.startsWith("D"))
                .findFirst();
        firstNameStartingWithD.ifPresent(name -> System.out.println("First name starting with 'D': " + name));
    }
}