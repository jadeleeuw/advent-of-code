package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Day03 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("03.txt")));
    }

    public List<String> inputReader(BufferedReader reader) {
        return reader.lines().toList();
    }

    public int logicPartOne(List<String> batteryBanks) {
        int totalJoltage = 0;
        for (int i = 0; i < batteryBanks.size(); i++) {
            String current = batteryBanks.get(i); // Get it? ;)
            int indexLeftNumber = indexOfHighestDigit(current);
            int indexRightNumber;

            // Rightmost number, so the second-highest number is the left number
            if (indexLeftNumber == current.length() - 1) {
                indexRightNumber = indexLeftNumber;
                indexLeftNumber = indexOfHighestDigit(current.substring(0, indexLeftNumber));
            } else {
                int relativeIndex = indexLeftNumber + 1;
                indexRightNumber = relativeIndex + indexOfHighestDigit(current.substring(relativeIndex));
            }

            String joltage = current.charAt(indexLeftNumber) + String.valueOf(current.charAt(indexRightNumber));
            System.out.println("%s -> %s".formatted(current, joltage));
            totalJoltage += Integer.parseInt(joltage);
        }
        return totalJoltage;
    }

    private int indexOfHighestDigit(String batteryBank) {
        char[] characters = batteryBank.toCharArray();

        int highestIndex = 0;
        int highestValue = Character.getNumericValue(characters[0]);
        for (int i = 0; i < characters.length; i++) {
            int currentValue = Character.getNumericValue(characters[i]);

            if (currentValue > highestValue) {
                highestValue = currentValue;
                highestIndex = i;
            }
        }
        return highestIndex;
    }

    public static void main(String[] args) {
        Day03 day3 = new Day03();
        System.out.println("Part 1:");
        System.out.println(day3.logicPartOne(day3.inputReader(day3.fetchResource())));
//        System.out.println("-----------------");
//        System.out.println("Part 2:");
//        System.out.println(day2.logicPartTwo(day2.inputReader(day2.fetchResource())));
    }
}
