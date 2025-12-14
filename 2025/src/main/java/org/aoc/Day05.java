package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;

public class Day05 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("05.txt")));
    }

    public IngredientInfo inputReader(BufferedReader reader) {
        List<String> input = reader.lines().toList();

        TreeSet<Range> freshIngredients = new TreeSet<>(comparing(Range::min));
        input.stream()
                .takeWhile(s -> !s.isBlank())
                .map(Range::from)
                .forEach(r -> addWithRangeOptimisation(r, freshIngredients));

        List<Long> ingredients = input.stream()
                .dropWhile(s -> !s.isBlank())
                // Drop the blank line
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        return new IngredientInfo(freshIngredients, ingredients);
    }

    void addWithRangeOptimisation(Range range, TreeSet<Range> existingRanges) {
        // Find first element that is higher than the min of the new entry
        Range higherElement = existingRanges.higher(range);

        // New entry is highest element
        if (higherElement == null) {
            existingRanges.add(range);
        }
        // There is overlap in the ranges, construct new range that covers the entire range
        else if (higherElement.min() <= range.max()) {
            existingRanges.remove(higherElement);
            existingRanges.add(new Range(range.min(), Math.max(higherElement.max(), range.max())));
        }
        // No overlap
        else {
            existingRanges.add(range);
        }
    }

    public record IngredientInfo(TreeSet<Range> freshIngredients, List<Long> ingredients) {}

    public record Range(long min, long max) implements Comparable<Range> {

        public static Range from(String range) {
            String[] splitted = range.split("-");
            return new Range(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]));
        }

        @Override
        public int compareTo(Range range) {
            return Long.compare(this.min(), range.min);
        }
    }

    public long numberOfFreshIngredients(IngredientInfo ingredientInfo) {
        int unfreshIngredients = 0;
        List<Long> sortedIngredients = ingredientInfo.ingredients().stream().sorted().toList();

        Iterator<Range> it = ingredientInfo.freshIngredients().iterator();
        Range current = it.next();
        for (long ingredient : sortedIngredients) {
            while (ingredient > current.max() && it.hasNext()) {
                current = it.next();
            }

            if (ingredient > current.max() || ingredient < current.min()) {
                unfreshIngredients++;
            }
        }

        return unfreshIngredients;
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
