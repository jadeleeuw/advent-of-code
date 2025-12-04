package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day02 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("example.txt")));
    }

    public List<Pair> inputReader(BufferedReader reader) {
        return reader.lines().map(this::toPair).toList();
    }

    private Pair toPair(String line) {
        String[] splitted = line.split("-");
        return new Pair(splitted[0], splitted[1]);
    }

    public record Pair(String left, String right) {}

    private Optional<String> invalidId(String id, long range, boolean isRight) {
        if (id.length() % 2 != 0) {
            return Optional.empty();
        }
        String toCopy = id.substring(0, id.length() / 2);
        String current = id.substring(id.length() / 2);

        if (isRight && Long.parseLong(current) >= Long.parseLong(toCopy) && Math.abs(Long.parseLong(toCopy+toCopy) - Long.parseLong(current)) <= range) {
            return Optional.of(toCopy + toCopy);
        }

        if (!isRight && Long.parseLong(current) <= Long.parseLong(toCopy) && Math.abs(Long.parseLong(toCopy+toCopy) - Long.parseLong(current)) <= range) {
            return Optional.of(toCopy+toCopy);
        }

//
//        if (isRight && Long.parseLong(current) < Long.parseLong(toCopy)) {
//            return Optional.empty();
//        }
//        if (!isRight && Long.parseLong(current) > Long.parseLong(toCopy)) {
//            return Optional.empty();
//        }
//
//        if (!isRight && Long.parseLong(id) + range >= Long.parseLong(toCopy+toCopy)) {
//            return Optional.of(toCopy+toCopy);
//        }
//        if (isRight && Long.parseLong(id) - range <= Long.parseLong(toCopy+toCopy)) {
//            return Optional.of(toCopy+toCopy);
//        }

        return Optional.empty();
    }

    public long logicPartOne(List<Pair> pairs) {
        return pairs.stream().flatMap(p -> {
            long range = Long.parseLong(p.right()) - Long.parseLong(p.left());
            return Stream.of(invalidId(p.left(), range, false), invalidId(p.right(), range, true)).distinct();
        }).flatMap(Optional::stream)
                .mapToLong(Long::parseLong)
                .peek(System.out::println)
                .sum();
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
