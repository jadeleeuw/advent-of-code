package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Day01 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("01.txt")));
    }

    public List<Integer> inputReader(BufferedReader reader) {
        return reader.lines().map(this::toNumber).toList();
    }

    private int toNumber(String input) {
        int number = Integer.parseInt(input.substring(1));
        if (input.charAt(0) == 'L') {
            number *= -1;
        }
        return number;
    }

    public int logicPartOne(List<Integer> input, int startingNumber) {
        int counter = 0;
        for (int number : input) {
            startingNumber = (startingNumber + number) % 100;
            if (startingNumber == 0) {
                counter++;
            }
        }
        return counter;
    }

    public int logicPartTwo(List<Integer> input, int startingNumber) {
        int counter = 0;
        int currentNumber = startingNumber;
        for (int number : input) {
            int timesPastZero = 0;
            int fullRotations = Math.abs(number / 100);
            timesPastZero += fullRotations;

            int newRelativeNumber = currentNumber + (number % 100);
            if (currentNumber != 0) {
                // If the current number is at 0 it does not count as going past 0
                if (newRelativeNumber <= 0 || newRelativeNumber >= 100) {
                    // Dial moved past or onto 0
                    timesPastZero++;
                }
            }

            int newNumber = newRelativeNumber % 100;
            if (newNumber < 0) {
                // Ensure number is not negative when moving left past 0
                newNumber = 100 + newNumber;
            }

            System.out.println("%s, %s -> %s, went past zero %s times".formatted(number, currentNumber, newNumber, timesPastZero));
            counter += timesPastZero;
            currentNumber = newNumber;
        }
        return counter;
    }

    public static void main(String[] args) {
        Day01 day1 = new Day01();
        System.out.println("Part 1:");
        System.out.println(day1.logicPartOne(day1.inputReader(day1.fetchResource()), 50));
        System.out.println("-----------------");
        System.out.println("Part 2:");
        System.out.println(day1.logicPartTwo(day1.inputReader(day1.fetchResource()), 50));
    }
}
