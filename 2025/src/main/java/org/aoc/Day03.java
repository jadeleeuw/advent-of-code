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

    public long totalJoltage(List<String> batteryBanks, int numberOfBatteries) {
        long totalJoltage = 0;
        for (int i = 0; i < batteryBanks.size(); i++) {
            String current = batteryBanks.get(i);
            StringBuilder batteryBuilder = new StringBuilder();

            for (int j = numberOfBatteries - 1; j >= 0; j--) {
                int index = indexOfHighestDigit(current.substring(0, current.length() - j));
                batteryBuilder.append(current.charAt(index));
                current = current.substring(index + 1);
            }

            String joltage = batteryBuilder.toString();
            System.out.println("%s -> %s".formatted(batteryBanks.get(i), joltage));
            totalJoltage += Long.parseLong(joltage);
        }
        return totalJoltage;
    }

    public static void main(String[] args) {
        Day03 day3 = new Day03();
        System.out.println("Part 1:");
        System.out.println(day3.totalJoltage(day3.inputReader(day3.fetchResource()), 2));
        System.out.println("-----------------");
        System.out.println("Part 2:");
        System.out.println(day3.totalJoltage(day3.inputReader(day3.fetchResource()), 12));
    }
}
