package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<List<Integer>> parse = parse(input);
        parse.forEach(Collections::sort);
        List<Integer> listA = parse.get(0);
        List<Integer> listB = parse.get(1);
        return IntStream.range(0, listB.size()).map(i -> Math.abs(listA.get(i) - listB.get(i))).sum();
    }

    @Override
    public Object part2(String input) {
        List<List<Integer>> parse = parse(input);
        List<Integer> listA = parse.get(0);
        List<Integer> listB = parse.get(1);
        return listA.stream().mapToInt(a -> a * (int) listB.stream().filter(b -> b.equals(a)).count()).sum();
    }

    List<List<Integer>> parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(s -> s.split(" {3}"))
                .collect(Collectors.teeing(
                        Collectors.mapping(arr -> Integer.valueOf(arr[0]), Collectors.toList()),
                        Collectors.mapping(arr -> Integer.valueOf(arr[1]), Collectors.toList()),
                        List::of
                ));
    }
}
