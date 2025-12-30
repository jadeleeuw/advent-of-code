package tech.picnic;

import static tech.picnic.Part1.TemperatureZone.AMBIENT;
import static tech.picnic.Part1.TemperatureZone.CHILLED;
import static tech.picnic.Part1.TemperatureZone.FROZEN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 {

    public BufferedReader fetchResource() {
        return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("Picnic.txt")));
    }

    public List<Tote> inputReader(BufferedReader reader) throws IOException {
        return new ArrayList<>(Arrays.stream(reader.readLine().split(" "))
                .map(Tote::from)
                .toList());
    }

    public record Tote(int weight, TemperatureZone tempZone) {
        public static Tote from(String input) {
            TemperatureZone tempZone = switch (input.charAt(0)) {
                case 'A' -> AMBIENT;
                case 'C' -> CHILLED;
                case 'F' -> FROZEN;
                default -> throw new RuntimeException("Error parsing temperature zone from input %s".formatted(input));
            };
            int weight = Integer.parseInt(input.substring(1));
            return new Tote(weight, tempZone);
        }
    }

    public enum TemperatureZone {
        AMBIENT(3),
        CHILLED(2),
        FROZEN(1);

        private int coldness;

        TemperatureZone(int coldness) {
            this.coldness = coldness;
        }

        public boolean isColderThan(TemperatureZone other) {
            return this.coldness < other.coldness;
        }
    }

    public record ToteStack(List<Tote> totes) {
    }

    public record ToteBatch(List<Tote> batch) {
    }

    public record AllTotesProcessed(List<ToteStack> stacks) {
    }

    public List<ToteBatch> createBatches(List<Tote> upcomingTotes) {
        List<ToteBatch> batches = new ArrayList<>();
        List<Tote> currentBatch = new ArrayList<>();
        Tote previousTote = upcomingTotes.getFirst();
        currentBatch.add(previousTote);

        for (int i = 1; i < upcomingTotes.size(); i++) {
            Tote currentTote = upcomingTotes.get(i);
            // Tote cannot be stacked on top of the previous tote, so this needs to be a new batch
            if (currentTote.tempZone().isColderThan(previousTote.tempZone()) || currentTote.weight() > previousTote.weight()) {
                batches.add(new ToteBatch(List.copyOf(currentBatch)));
                currentBatch = new ArrayList<>();
            }
            currentBatch.add(currentTote);
            previousTote = currentTote;
        }
        batches.add(new ToteBatch(List.copyOf(currentBatch)));
        return batches;
    }

    public List<AllTotesProcessed> buildAllPossibilities(List<ToteStack> processedStacks, List<Tote> upcomingTotes) {
        if (upcomingTotes.isEmpty()) {
            return List.of(new AllTotesProcessed(processedStacks));
        }

        List<AllTotesProcessed> processed = new ArrayList<>();
        for (int i = 1; i <= Math.min(4, upcomingTotes.size()); i++) {
            ToteStack newStack = new ToteStack(upcomingTotes.subList(0, i));
            List<ToteStack> processedStacksCopy = new ArrayList<>(processedStacks);
            processedStacksCopy.add(newStack);
            processed.addAll(buildAllPossibilities(processedStacksCopy, new ArrayList<>(upcomingTotes.subList(i, upcomingTotes.size()))));
        }
        return processed;
    }

    public long minimalCost(List<Tote> upcomingTotes) {
        long totalCost = 0;
        // Batch the tote list to reduce the number of possible tote stack combinations
        List<ToteBatch> batches = createBatches(upcomingTotes);
        for (ToteBatch batch : batches) {
            // Find all possible stack combinations, and use the lowest cost one
            totalCost += buildAllPossibilities(new ArrayList<>(), batch.batch())
                    .stream()
                    .mapToLong(this::cost)
                    .min()
                    .orElseThrow(() -> new RuntimeException("Could not find any possible options of stacking the totes"));
        }
        return totalCost;
    }

    public long cost(AllTotesProcessed processed) {
        return processed.stacks().stream()
                .mapToLong(this::cost)
                .sum();
    }

    public int cost(ToteStack stack) {
        return switch (stack.totes().size()) {
            case 1 -> 50;
            case 2 -> 25;
            case 3 -> 10;
            case 4 -> 0;
            default ->
                    throw new RuntimeException("Stack size can only by 1 - 4, but was: %s, %s".formatted(stack.totes().size(), stack));
        };
    }


    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        System.out.println("Part 1:");
        System.out.println(part1.minimalCost(part1.inputReader(part1.fetchResource())));

//        System.out.println(part1.minimalCost(new ArrayList<>(),  part1.inputReader(part1.fetchResource())));
//        System.out.println("-----------------");
//        System.out.println("Part 2:");
//        System.out.println(day1.logicPartTwo(day1.inputReader(day1.fetchResource()), 50));
    }
}
