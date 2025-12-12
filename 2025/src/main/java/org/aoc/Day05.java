package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day05 {



    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("example.txt")));
    }

    public IngredientInfo inputReader(BufferedReader reader) {
        boolean processingRanges = true;
        List<Long> ingredients = new ArrayList<>();
        for (String line : reader.lines().toList()) {
            if (line.equals("")) {
                processingRanges = false;
                continue;
            }

            processingRanges ? Range.from(line) : Long.parseLong(line)
        }


        reader.lines().takeWhile();
        reader.lines().

        String input = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        String[] splitted = input.split("^%");
        return new IngredientInfo(new TreeSet<Range>(), List.of());
    }

    public record IngredientInfo(TreeSet<Range> freshIngredients, List<Long> ingredients) {}

    public record Range(long from, long to) {

        public static Range from(String range) {
            String[] splitted = range.split("-");
            return new Range(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]));
        }
    }

    public long numberOfFreshIngredients(IngredientInfo ingredientInfo) {
        return 0L;
    }

    public static void main(String[] args) {
        Day05 day5 = new Day05();
        System.out.println("Part 1:");
        System.out.println(day5.numberOfFreshIngredients(day5.inputReader(day5.fetchResource())));
//        System.out.println("-----------------");
//        System.out.println("Part 2:");
//        System.out.println(day5.removeableRolls(day5.inputReader(day5.fetchResource())));

    }
}
