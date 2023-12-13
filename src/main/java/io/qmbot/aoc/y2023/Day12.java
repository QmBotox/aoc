package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Day12 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Spring> strings = parse(input);
        int result = 0;
        for (Spring spring : strings) {
            result += arrangements(spring);
        }
        return result;
    }

    @Override
    public Object part2(String input) {
        List<Spring> strings = parse(input);
        long result = 0;
        for (Spring spring : strings) {
            spring.unfold();
            result += arrangements(spring);
        }
        return result;
    }

    long arrangements(Spring spring) {
        List<String> strings = new ArrayList<>();
        strings.add("");
        for (int i = 0; i<spring.springs.length(); i++) {
            if (spring.springs.charAt(i) == '?') {
                List<String> operational = strings.stream().map(s -> s + '.').toList();
                List<String> damaged = strings.stream().map(s -> s + '#').toList();
                strings.clear();
                for (String s : operational) {
                    if (spring.trueForString2(s)) {
                        strings.add(s);
                    }
                }
                for (String s : damaged) {
                    if (spring.trueForString2(s)) {
                        strings.add(s);
                    }
                }
            } else {
                int j = i;
                List<String> next = strings.stream().map(s -> s + spring.springs.charAt(j)).toList();
                strings.clear();
                for (String s : next) {
                    if (spring.trueForString2(s)) {
                        strings.add(s);
                    }
                }
            }
        }

        return strings.stream().filter(spring::trueForString).count();
    }

    List<Spring> parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(Day12::spring).collect(Collectors.toList());
    }
    static Spring spring(String string) {
        String[] parts = string.split(" ");
        return new Spring(parts[0], Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList());
    }

    static class Spring {
        String springs;
        List<Integer> groups;

        @Override
        public String toString() {
            return "Spring{" +
                    "springs='" + springs + '\'' +
                    ", groups=" + groups +
                    '}';
        }

        public Spring(String springs, List<Integer> groups) {
            this.springs = springs;
            this.groups = groups;
        }

        boolean trueForString(String springs) {
            String[] groupString = Arrays.stream(springs.split("\\."))
                    .filter(str -> !str.isEmpty())
                    .toArray(String[]::new);
            if (groups.size() != groupString.length) return false;
            for (int i = 0; i< groups.size(); i++) {
                if (groups.get(i) != groupString[i].length()) return false;
            }
            return true;
        }

        boolean trueForString2(String springs) {
            String[] groupString = Arrays.stream(springs.split("\\.")).filter(str -> !str.isEmpty()).toArray(String[]::new);
            if (groups.size() < groupString.length) return false;
            for (int i = 0; i< groupString.length - 1; i++) {
                if (groups.get(i) != groupString[i].length()) return false;
            }
            return true;
        }

        void unfold() {
            StringJoiner joiner = new StringJoiner("?");
            List<Integer> g = new ArrayList<>();
            for (int i = 0; i<5; i++) {
                joiner.add(springs);
                g.addAll(groups);
            }
            this.springs = String.valueOf(joiner);
            this.groups = g;
        }
    }
}
