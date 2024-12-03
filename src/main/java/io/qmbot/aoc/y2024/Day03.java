package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;

import java.util.stream.IntStream;

public class Day03 implements Puzzle {
    @Override
    public Object part1(String input) {
        return IntStream.range(0, input.length() - 4).map(i -> mul(input, i)).sum();
    }

    @Override
    public Object part2(String input) {
        int result = 0;
        boolean on = true;
        for (int i = 0; i < input.length() - 4; i++) {
            on = !input.startsWith("don't()", i) && (on || input.startsWith("do()", i));
            if (on) {
                result += mul(input, i);
            }
        }
        return result;
    }

    int mul(String input, int i) {
        if (input.startsWith("mul(", i)) {
            String[] numbString = input.substring(i + 4, input.indexOf(')', i)).split(",");
            if (numbString.length == 2 && numbString[0].matches("\\d+") && numbString[1].matches("\\d+")) {
                return Integer.parseInt(numbString[0]) * Integer.parseInt(numbString[1]);
            }
        }
        return 0;
    }

}
