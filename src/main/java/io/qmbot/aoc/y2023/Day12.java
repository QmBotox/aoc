package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 implements Puzzle {
    @Override
    public Object part1(String input) {
        return parse(input).stream().mapToLong(Day12::countOfSolutions).sum();
    }

    @Override
    public Object part2(String input) {
        return parse(input).stream().mapToLong(spring -> countOfSolutions(spring.unfold())).sum();
    }

    List<Spring> parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(Day12::spring).collect(Collectors.toList());
    }

    static Spring spring(String string) {
        String[] parts = string.split(" ");
        return new Spring(parts[0], Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList());
    }

    static long countOfSolutions(Spring spring) {
        return countOfSolutions(spring, new HashMap<>());
    }

    static long countOfSolutions(Spring spring, Map<Spring, Long> results) {
        if (results.containsKey(spring)) return results.get(spring);
        String springs = spring.springs;
        int springsLength = springs.length();
        List<Integer> groups = spring.groups;
        if (groups.isEmpty() && !springs.contains("#")) return 1;
        if (springs.isEmpty() || groups.isEmpty()) return 0;
        String withoutFirstChar = springs.substring(1);
        switch (springs.charAt(0)) {
            case '.' -> {
                return countOfSolutions(new Spring(withoutFirstChar, groups), results);
            }
            case '?' -> {
                return countOfSolutions(new Spring('.' + withoutFirstChar, groups), results)
                        + countOfSolutions(new Spring('#' + withoutFirstChar, groups), results);
            }
            case '#' -> {
                int index = groups.get(0);
                if (springsLength < index || (springsLength != index && springs.charAt(index) == '#')) return 0;
                for (int i = 0; i < index; i++) {
                    if (springs.charAt(i) == '.') return 0;
                }
                List<Integer> newGroups = new ArrayList<>(groups);
                newGroups.remove(0);
                long result = countOfSolutions(
                        new Spring(index + 1 > springsLength ? "" : springs.substring(index + 1), newGroups), results);
                results.put(spring, result);
                return result;
            }
            default -> {}
        }
        throw new IllegalStateException();
    }

    record Spring(String springs, List<Integer> groups) {
        Spring unfold() {
            StringJoiner joiner = new StringJoiner("?");
            List<Integer> g = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                joiner.add(springs);
                g.addAll(groups);
            }
            return new Spring(String.valueOf(joiner), g);
        }
    }
}
