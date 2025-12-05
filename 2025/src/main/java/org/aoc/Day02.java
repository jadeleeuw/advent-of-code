package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day02 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("02.txt")));
    }

    public List<Pair> inputReader(BufferedReader reader) {
        return reader.lines().map(this::toPair).toList();
    }

    private Pair toPair(String line) {
        String[] splitted = line.split("-");
        return new Pair(splitted[0], splitted[1]);
    }

    public record Pair(String left, String right) {
    }

    private List<Long> invalidIds(Pair input) {
        ArrayList<Long> result = new ArrayList<>();

        long range = Long.parseLong(input.right()) - Long.parseLong(input.left());

        // Ensure that the left side is at least one digit long
        long toCopyLeft = Long.parseLong(input.left().substring(0, 1));
        if (input.left().length() > 1) {
            toCopyLeft = Long.parseLong(input.left().substring(0, input.left().length() / 2));
        }
        long toCopyRight = Long.parseLong(input.right().substring(0, (int) Math.ceil(input.right().length() / 2.0)));

        while (toCopyLeft <= toCopyRight) {
            long invalidId = Long.parseLong(toCopyLeft + String.valueOf(toCopyLeft));
            if (invalidId >= Long.parseLong(input.left())) {
                if (Long.parseLong(input.left()) + range >= invalidId) {
                    result.add(invalidId);
                }
            }
            toCopyLeft++;
        }
        return result;
    }

    public long logicPartOne(List<Pair> pairs) {
        return pairs.stream().map(this::invalidIds).peek(System.out::println).flatMap(List::stream).mapToLong(l -> l).sum();
    }

    public static void main(String[] args) {
        Day02 day2 = new Day02();
        System.out.println("Part 1:");
        System.out.println(day2.logicPartOne(day2.inputReader(day2.fetchResource())));
        System.out.println("-----------------");
//        System.out.println("Part 2:");
//        System.out.println(day2.logicPartTwo(day2.inputReader(day2.fetchResource()), 50));
    }
}
