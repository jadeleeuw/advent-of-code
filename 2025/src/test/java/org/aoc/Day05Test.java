package org.aoc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class Day05Test {

//    @Test
//    @DisplayName("Should add new range to set if it is the lowest entry")
//    void addWithRangeOptimisation1() {
//        Day05.Range newRange = new Day05.Range(0, 5);
//        Day05.Range existingRange = new Day05.Range(6, 10);
//        TreeSet<Day05.Range> set = new TreeSet<>(List.of(existingRange));
//        new Day05().addWithRangeOptimisation(newRange, set);
//
//        assertThat(set).containsExactlyInAnyOrder(newRange, existingRange);
//    }
//
//    @Test
//    @DisplayName("Should add new range to set if it is the highest entry")
//    void addWithRangeOptimisation2() {
//        Day05.Range newRange = new Day05.Range(6, 10);
//        Day05.Range existingRange = new Day05.Range(0, 5);
//        TreeSet<Day05.Range> set = new TreeSet<>(List.of(existingRange));
//        new Day05().addWithRangeOptimisation(newRange, set);
//
//        assertThat(set).containsExactlyInAnyOrder(newRange, existingRange);
//    }
//    @Test
//    @DisplayName("Should add new range to set if there is no overlap with any another entry")
//    void addWithRangeOptimisation3() {
//        Day05.Range newRange = new Day05.Range(6, 10);
//        Day05.Range existingRange1 = new Day05.Range(0, 5);
//        Day05.Range existingRange2 = new Day05.Range(11, 15);
//        TreeSet<Day05.Range> set = new TreeSet<>(List.of(existingRange1, existingRange2));
//        new Day05().addWithRangeOptimisation(newRange, set);
//
//        assertThat(set).containsExactlyInAnyOrder(newRange, existingRange1, existingRange2);
//    }
//
//    @Test
//    @DisplayName("Should create new range extending the range if there is overlap and remove the old range")
//    void addWithRangeOptimisation4() {
//        Day05.Range newRange = new Day05.Range(0, 5);
//        Day05.Range existingRange = new Day05.Range(3, 7);
//        TreeSet<Day05.Range> set = new TreeSet<>(List.of(existingRange));
//        new Day05().addWithRangeOptimisation(newRange, set);
//
//        assertThat(set).containsExactly(new Day05.Range(0, 7));
//    }

    @Test
    @DisplayName("Should keep old range if the range is entirely contained in another range")
    void addWithRangeOptimisation5() {
        Day05.Range newRange = new Day05.Range(0, 5);
        Day05.Range existingRange = new Day05.Range(-1, 6);
        TreeSet<Day05.Range> set = new TreeSet<>(List.of(existingRange));
        new Day05().addWithRangeOptimisation(newRange, set);

        assertThat(set).containsExactly(existingRange);
    }


//    @Test
//    @DisplayName(")
//    void numberOfFreshIngredients1() {
//        Day05.IngredientInfo ingredientInfo = new Day05.IngredientInfo()
//        assertThat(new Day05().numberOfFreshIngredients(List.of(new Day02.Pair("11", "12")))).isEqualTo(11);
//    }
}
