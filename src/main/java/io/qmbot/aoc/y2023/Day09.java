package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day09 implements Puzzle {
    @Override
    public Object part1(String input) {
        return parse(input).stream().mapToInt(history -> -nextValue(history.values)).sum();
    }

    @Override
    public Object part2(String input) {
        return parse(input).stream().mapToInt(history -> beginning(history.values)).sum();
    }

    private static List<History> parse(String input) {
        List<History> histories = new ArrayList<>();
        for (String str : input.split(REGEX_NEW_LINE)) {
            histories.add(new History(Arrays.stream(Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray())
                    .boxed().toList()));
        }
        return histories;
    }

    int nextValue(List<Integer> values) {
        if (values.stream().allMatch(element -> element.equals(0))) return 0;
        List<Integer> v = IntStream.range(1, values.size()).map(i -> values.get(i) - values.get(i - 1)).boxed().toList();
        return nextValue(v) - values.get(v.size());
    }

    int beginning(List<Integer> values) {
        if (values.stream().allMatch(element -> element.equals(0))) return 0;
        List<Integer> v = IntStream.range(1, values.size()).map(i -> values.get(i) - values.get(i - 1)).boxed().toList();
        return values.get(0) - beginning(v);
    }
    static class History{
        List<Integer> values;
        public History(List<Integer> values) {
            this.values = values;
        }
    }
}
