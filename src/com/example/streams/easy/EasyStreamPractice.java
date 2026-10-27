// package com.example.streams.easy;

// import java.util.Arrays;
// import java.util.Comparator;
// import java.util.List;
// import java.util.stream.Collectors;

// public class EasyStreamPractice {

//     public static void main(String[] args) {

//         List<Integer> numbers = Arrays.asList(5, 2, 8, 2, 9, 1, 5, 10, 3);
//         List<String> words = Arrays.asList("java", "stream", "api", "lambda", "collector");

//         // 1. Find Distinct Elements
//         System.out.println("--- 1. DISTINCT ELEMENTS ---");
//         List<Integer> uniqueNumbers = numbers.stream()
//                 .distinct()
//                 .collect(Collectors.toList());
//         System.out.println("Unique Numbers: " + uniqueNumbers);

//         // 2. Sorting Elements (Natural and Reverse Order)
//         System.out.println("\n--- 2. SORTING ELEMENTS ---");
//         List<Integer> sortedAscending = numbers.stream()
//                 .sorted()
//                 .collect(Collectors.toList());
//         System.out.println("Ascending Order: " + sortedAscending);

//         List<Integer> sortedDescending = numbers.stream()
//                 .sorted(Comparator.reverseOrder())
//                 .collect(Collectors.toList());
//         System.out.println("Descending Order: " + sortedDescending);

//         // 3. Joining Strings with Delimiter
//         System.out.println("\n--- 3. JOINING STRINGS ---");
//         String joinedWords = words.stream()
//                 .map(String::toUpperCase)
//                 .collect(Collectors.joining(", ", "[", "]"));
//         System.out.println("Joined String: " + joinedJoinedString(joinedWords));

//         // 4. Find Min and Max Values
//         System.out.println("\n--- 4. MIN AND MAX VALUES ---");
//         Integer minVal = numbers.stream()
//                 .min(Integer::compareTo)
//                 .orElse(0);
//         Integer maxVal = numbers.stream()
//                 .max(Integer::compareTo)
//                 .orElse(0);
//         System.out.println("Min Value: " + minVal + ", Max Value: " + maxVal);

//         // 5. Match Predicates (anyMatch, allMatch, noneMatch)
//         System.out.println("\n--- 5. MATCHING OPERATIONS ---");
//         boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
//         boolean allPositive = numbers.stream().allMatch(n -> n > 0);
//         boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
//         System.out.println("Has Even: " + hasEven + ", All Positive: " + allPositive + ", None Negative: " + noneNegative);

//         // 6. Limit and Skip Operations
//         System.out.println("\n--- 6. LIMIT AND SKIP ---");
//         List<Integer> top3AfterSkipping2 = numbers.stream()
//                 .distinct()
//                 .sorted(Comparator.reverseOrder())
//                 .skip(2)
//                 .limit(3)
//                 .collect(Collectors.toList());
//         System.out.println("Top 3 after skipping first 2: " + top3AfterSkipping2);
//     }

//     private static String joinedJoinedString(String str) {
//         return str;
//     }
// }
