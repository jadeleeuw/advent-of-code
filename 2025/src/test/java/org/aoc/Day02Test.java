package org.aoc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Day02Test {

    @Test
    @DisplayName("Should find invalid id equal to left side number")
    void logicPartOne1() {
        assertThat(new Day02().logicPartOne(List.of(new Day02.Pair("11", "12")))).isEqualTo(11);
    }

    @Test
    @DisplayName("Should find invalid id equal to right side number")
    void logicPartOne2() {
        assertThat(new Day02().logicPartOne(List.of(new Day02.Pair("21", "22")))).isEqualTo(22);
    }

    @Test
    @DisplayName("Should find all invalids id within range")
    void logicPartOne3() {
        assertThat(new Day02().logicPartOne(List.of(new Day02.Pair("10", "34")))).isEqualTo(11 + 22 + 33);
    }

    @Test
    @DisplayName("Should only find invalid ids that are higher than the left side number")
    void logicPartOne4() {
        assertThat(new Day02().logicPartOne(List.of(new Day02.Pair("12", "14")))).isEqualTo(0);
    }

    @Test
    @DisplayName("Should only find invalid ids that are lower than the right side number")
    void logicPartOne5() {
        assertThat(new Day02().logicPartOne(List.of(new Day02.Pair("8", "10")))).isEqualTo(0);
    }

    @Test
    @DisplayName("Should find invalid ids if right side number has more digits than the left side number")
    void logicPartOne6() {
        assertThat(new Day02().logicPartOne(List.of(new Day02.Pair("95", "115")))).isEqualTo(99);
    }
}
