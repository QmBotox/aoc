package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day13 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<char[][]> patterns = parse(input);
        int everyC = 0;
        int everyR = 0;
        for (char[][] p : patterns) {
            everyC += columnsMirror(p, false);
            everyR += rowsMirror(p, false);
            int k = 0;
        }
        return everyC + (100 * everyR);
    }

    @Override
    public Object part2(String input) {
        List<char[][]> patterns = parse(input);
        int everyC = 0;
        int everyR = 0;
        for (char[][] p : patterns) {
            int c = columnsMirror(p, false);
            int r = rowsMirror(p, false);
            int cChange = columnsMirror(p ,true);
            int rChange = rowsMirror(p, true);
            everyC += cChange == c ?  0 : cChange;
            everyR += rChange == r ? 0 : rChange;
            int k = 0;
        }
        return everyC + (100 * everyR);
    }

    List<char[][]> parse(String input) {
        List<char[][]> patterns = new ArrayList<>();
        for (String string : input.split(REGEX_EMPTY_LINE)) {
            patterns.add(pattern(string));
        }
        return patterns;
    }

    char[][] pattern(String string) {
        String[] strings = string.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        int sizeX = strings[0].length();
        char[][] pattern = new char[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                pattern[y][x] = strings[y].charAt(x);
            }
        }
        return pattern;
    }

    int columnsMirror(char[][] pattern, boolean part2) {
        int sizeX = pattern[0].length;
        for (int x = 0; x < sizeX - 1; x++) {
            String left = column(x, pattern);
            String right = column(x + 1, pattern);
            if (left.equals(right)  || (part2)) {
                if (fullMirrorColumns(pattern, x, part2)) return x + 1;

            }
        }
        return 0;
    }

    int rowsMirror(char[][] pattern, boolean part2) {
        int sizeY = pattern.length;
        for (int y = 0; y < sizeY - 1; y++) {
            String up = row(y, pattern);
            String down = row(y + 1, pattern);
            if (up.equals(down) || part2) {
                if (fullMirrorRows(pattern, y, part2)) return y + 1;
            }
        }
        return 0;
    }

    String row(int y, char[][] pattern) {
        StringBuilder row = new StringBuilder();
        for (int x = 0; x < pattern[0].length; x++) {
            row.append(pattern[y][x]);
        }
        return String.valueOf(row);
    }

    boolean fullMirrorRows(char[][] pattern, int y, boolean part2) {
        int sizeY = pattern.length;
        String first = row(y, pattern);
        String second = row(y + 1, pattern);
        int w = 0;
        boolean changed = false;
        if (part2 && oneDifferent(first, second)) {
            first = "";
            second = "";
            w++;
            changed = true;
        }
        while (first.equals(second) && (((y - w) > -1) && (y + 1 + w < sizeY))) {
            first = row(y - w, pattern);
            second = row(y + 1 + w, pattern);
            w++;
            if (part2 && !changed && oneDifferent(first, second)) {
                first = "";
                second = "";
                changed = true;
            }
        }
        if (!first.equals(second)) return false;
        return y - w == -1 || y + 1 + w == sizeY;
    }

    boolean oneDifferent(String first, String second) {
        int count = 0;
        for(int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) count++;
        }
        return count == 1;
    }

    boolean fullMirrorColumns(char[][] pattern, int x, boolean part2) {
        int sizeX = pattern[0].length;
        String first = column(x, pattern);
        String second = column(x + 1, pattern);
        int w = 0;
        boolean changed = false;
        if (part2 && oneDifferent(first, second)) {
            first = "";
            second = "";
            w++;
            changed = true;
        }
        while (first.equals(second) && (((x - w) > -1) && (x + 1 + w < sizeX))) {
            first = column(x - w, pattern);
            second = column(x + 1 + w, pattern);
            w++;
            if (part2 && !changed && oneDifferent(first, second)) {
                first = "";
                second = "";
                changed = true;
            }
        }
        if (!first.equals(second)) return false;
        return x - w == -1 || x + 1 + w == sizeX;
    }

    String column(int x, char[][] pattern) {
        StringBuilder column = new StringBuilder();
        for (char[] chars : pattern) {
            column.append(chars[x]);
        }
        return column.toString();
    }
}
