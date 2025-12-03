package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Day02 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("01.txt")));
    }

    public List<Pair> inputReader(BufferedReader reader) {
        return reader.lines()
                .map(this::toPair)
                .toList();
    }

    private Pair toPair(String line) {
        String[] splitted = line.split("-");
        return new Pair(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
    }

    public record Pair(int left, int right) {

        public List<Integer> invalidIds() {
            String leftString = String.valueOf(left());
            leftString.substring()

        }
    }



    public int logicPartOne() {

    }

    public static void main(String[] args) {
        Day02 day2 = new Day02();
        System.out.println("Part 1:");
        System.out.println(day2.logicPartOne(day2.inputReader(day2.fetchResource()), 50));
        System.out.println("-----------------");
        System.out.println("Part 2:");
        System.out.println(day2.logicPartTwo(day2.inputReader(day2.fetchResource()), 50));
    }
}
