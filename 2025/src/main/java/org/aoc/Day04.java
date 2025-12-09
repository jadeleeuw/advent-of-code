package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;

public class Day04 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("04.txt")));
    }

    public List<String> inputReader(BufferedReader reader) {
        return reader.lines().toList();
    }

    public record Coordinate(int x, int y) {
    }

    public long rollsAccessibleByForklift(List<String> map) {
        ArrayList<Coordinate> adjacentToRolls = new ArrayList<>();
        for (int y = 0; y < map.size(); y++) {
            char[] row = map.get(y).toCharArray();
            for (int x = 0; x < row.length; x++) {
                if (row[x] == '@') {
                    adjacentToRolls.addAll(
                            List.of(new Coordinate(x - 1, y - 1),
                                    new Coordinate(x, y - 1),
                                    new Coordinate(x + 1, y - 1),
                                    new Coordinate(x - 1, y),
                                    new Coordinate(x + 1, y),
                                    new Coordinate(x - 1, y + 1),
                                    new Coordinate(x, y + 1),
                                    new Coordinate(x + 1, y + 1)));
                }
            }
        }

        var a = adjacentToRolls.stream()
                .filter(c -> validCoordinate(c, map.getFirst().length(), map.size()))
                .collect(groupingBy(Function.identity()))
                .entrySet().stream()
                // all locations that have less than 4 adjacent forklifts
                .filter(e -> e.getValue().size() < 4)
                .map(Map.Entry::getKey)
                .filter(c -> isRollOfPaper(c, map)).toList();
        printMap(a, map);

        return adjacentToRolls.stream()
                .filter(c -> validCoordinate(c, map.getFirst().length(), map.size()))
                .collect(groupingBy(Function.identity()))
                .entrySet().stream()
                // all locations that have less than 4 adjacent forklifts
                .filter(e -> e.getValue().size() < 4)
                .map(Map.Entry::getKey)
                .filter(c -> isRollOfPaper(c, map))
                .count();
    }

    private boolean validCoordinate(Coordinate c, int xLength, int yLength) {
        return c.x() >= 0 && c.x() < xLength && c.y() >= 0 && c.y() < yLength;
    }

    private boolean isRollOfPaper(Coordinate c, List<String> map) {
        return map.get(c.y()).charAt(c.x()) == '@';
    }

    private void printMap(List<Coordinate> coordinates, List<String> original) {
        int xLength = original.getFirst().length();
        int yLength = original.size();

        Character[][] map = new Character[yLength][xLength];
        for (Coordinate c : coordinates) {
            map[c.y()][c.x()] = 'x';
        }

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                Character current = map[y][x];
                if (current == null) {
                    current = original.get(y).charAt(x);
                }
                System.out.print(current);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Day04 day4 = new Day04();
        System.out.println("Part 1:");
        System.out.println(day4.rollsAccessibleByForklift(day4.inputReader(day4.fetchResource())));
//        System.out.println("-----------------");
//        System.out.println("Part 2:");
//        System.out.println(day3.totalJoltage(day3.inputReader(day3.fetchResource()), 12));
    }
}
