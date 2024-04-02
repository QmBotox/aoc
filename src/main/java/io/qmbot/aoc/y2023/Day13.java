package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 implements Puzzle {
    @Override
    public Object part1(String input) {
        return result(parse(input), 0);
    }

    @Override
    public Object part2(String input) {
        return result(parse(input), 1);
    }

    int result(List<char[][]> patterns, int differences) {
        return patterns.stream().mapToInt(p -> columnsMirror(p, differences)).sum()
                + (100 * patterns.stream().mapToInt(p -> rowsMirror(p, differences)).sum());
    }

    List<char[][]> parse(String input) {
        return Arrays.stream(input.split(REGEX_EMPTY_LINE)).map(this::pattern).collect(Collectors.toList());
    }

    char[][] pattern(String string) {
        String[] strings = string.split(REGEX_NEW_LINE);
        char[][] pattern = new char[strings.length][];
        for (int y = 0; y < strings.length; y++) {
            pattern[y] = strings[y].toCharArray();
        }
        return pattern;
    }

    int columnsMirror(char[][] pattern, int differences) {
        int sizeX = pattern[0].length;
        for (int x = 0; x < sizeX - 1; x++) {
            if (fullMirrorColumns(pattern, x, differences)) return x + 1;
        }
        return 0;
    }

    int rowsMirror(char[][] pattern, int differences) {
        int sizeY = pattern.length;
        for (int y = 0; y < sizeY - 1; y++) {
            if (fullMirrorRows(pattern, y, differences)) return y + 1;
        }
        return 0;
    }

    boolean fullMirrorRows(char[][] pattern, int y, int differences) {
        int actualDifferences = 0;
        int step = 0;
        while (((y - step) > -1) && (y + 1 + step < pattern.length)) {
            for (int i = 0; i < pattern[0].length; i++) {
                if (pattern[y - step][i] != pattern[y + 1 + step][i]) actualDifferences++;
            }
            step++;
        }
        return actualDifferences == differences;
    }

    boolean fullMirrorColumns(char[][] pattern, int x, int differences) {
        int actualDifferences = 0;
        int step = 0;
        while (((x - step) > -1) && (x + 1 + step < pattern[0].length)) {
            for (int i = 0; i < pattern.length; i++) {
                if (pattern[i][x - step] != pattern[i][x + 1 + step]) actualDifferences++;
            }
            step++;
        }
        return actualDifferences == differences;
    }
}
