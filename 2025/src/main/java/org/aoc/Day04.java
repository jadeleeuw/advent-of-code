package org.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day04 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("04.txt")));
    }

    public List<String> inputReader(BufferedReader reader) {
        return reader.lines().toList();
    }

    public record Coordinate(int x, int y) {
    }

    public Stream<Coordinate> rollsAccessibleByForklift(List<String> map) {
        Map<Coordinate, Integer> adjacentToRolls = new HashMap<>();

        for (int y = 0; y < map.size(); y++) {
            char[] row = map.get(y).toCharArray();
            for (int x = 0; x < row.length; x++) {
                if (row[x] == '@') {
                    adjacentToRolls.putIfAbsent(new Coordinate(x, y), 0);
                    List.of(
                            new Coordinate(x - 1, y - 1),
                            new Coordinate(x, y - 1),
                            new Coordinate(x + 1, y - 1),
                            new Coordinate(x - 1, y),
                            new Coordinate(x + 1, y),
                            new Coordinate(x - 1, y + 1),
                            new Coordinate(x, y + 1),
                            new Coordinate(x + 1, y + 1)).forEach(c -> {
                        int value = adjacentToRolls.getOrDefault(c, 0);
                        adjacentToRolls.put(c, value + 1);
                    });
                }
            }
        }

        return adjacentToRolls.entrySet().stream().filter(e -> validCoordinate(e.getKey(), map.getFirst().length(), map.size()))
                // all locations that have less than 4 adjacent forklifts
                .filter(e -> e.getValue() < 4).map(Map.Entry::getKey).filter(c -> isRollOfPaper(c, map));
    }

    private boolean validCoordinate(Coordinate c, int xLength, int yLength) {
        return c.x() >= 0 && c.x() < xLength && c.y() >= 0 && c.y() < yLength;
    }

    private boolean isRollOfPaper(Coordinate c, List<String> map) {
        return map.get(c.y()).charAt(c.x()) == '@';
    }

    private List<String> deleteRolls(List<Coordinate> toRemove, List<String> map) {
        ArrayList<String> mapCopy = new ArrayList<>(map);
        toRemove.forEach(c -> {
            char[] chars = mapCopy.get(c.y()).toCharArray();
            chars[c.x()] = '.';
            mapCopy.set(c.y(), new String(chars));
        });
        return mapCopy;
    }

    public long removeableRolls(List<String> map) {
        List<String> currentIteration = map;
        List<Coordinate> accessibleRolls = rollsAccessibleByForklift(map).toList();
        long counter = accessibleRolls.size();
        while (!accessibleRolls.isEmpty()) {
            currentIteration = deleteRolls(accessibleRolls, currentIteration);
            accessibleRolls = rollsAccessibleByForklift(currentIteration).toList();
            counter += accessibleRolls.size();
        }
        return counter;
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
        System.out.println(day4.rollsAccessibleByForklift(day4.inputReader(day4.fetchResource())).count());
        System.out.println("-----------------");
        System.out.println("Part 2:");
        System.out.println(day4.removeableRolls(day4.inputReader(day4.fetchResource())));
    }
}
