package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day11 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Point> galaxies = parse(input);
        String[] strings = input.split(REGEX_NEW_LINE);
        long spaceExpands = 1;

        column(strings, galaxies, spaceExpands);
        rows(strings, galaxies, spaceExpands);

        long result = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result += shortPath(galaxies.get(i), galaxies.get(j));
            }
        }
        return result;
    }

    @Override
    public Object part2(String input) {
        List<Point> galaxies = parse(input);
        String[] strings = input.split(REGEX_NEW_LINE);
        long spaceExpands = 10;

        column(strings, galaxies, spaceExpands);
        rows(strings, galaxies, spaceExpands);

        long result = 0L;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result += shortPath(galaxies.get(i), galaxies.get(j));
            }
        }
        return result;
    }

    void column(String[] strings, List<Point> galaxies, long spaceExpands) {
        for (int x = strings[0].length(); x > -1; x--) {
            boolean haveGalaxy = false;
            for (Point galaxy : galaxies) {
                if (galaxy.x == x) {
                    haveGalaxy = true;
                    break;
                }
            }
            if (!haveGalaxy) {
                for (Point galaxy : galaxies) {
                    if (galaxy.x > x) galaxy.x += spaceExpands;
                }
            }
        }
    }

    void rows(String[] strings, List<Point> galaxies, long spaceExpands) {
        for (int y = strings.length - 1; y > -1; y--) {
            boolean haveGalaxy = false;
            for (Point galaxy : galaxies) {
                if (galaxy.y == y) {
                    haveGalaxy = true;
                    break;
                }
            }
            if (!haveGalaxy) {
                for (Point galaxy : galaxies) {
                    if (galaxy.y > y) galaxy.y += spaceExpands;
                }
            }
        }
    }

    List<Point> parse(String input) {
        List<Point> galaxies = new ArrayList<>();
        String[] strings = input.split(REGEX_NEW_LINE);
        for (int y = 0; y < strings.length; y++) {
            for (int x = 0; x < strings[0].length(); x++) {
                if (strings[y].charAt(x) == '#') {
                    galaxies.add(new Point(y, x));
                }
            }
        }
        return galaxies;
    }

    static long shortPath(Point first, Point second) {
        long y = Math.max(first.y, second.y) - Math.min(first.y, second.y) + 1;
        long x = Math.max(first.x, second.x) - Math.min(first.x, second.x) + 1;
        return Math.max(y, x) - 2 + Math.min(y, x);
    }

    static class Point {
        long y;
        long x;

        public Point(long y, long x) {
            this.y = y;
            this.x = x;
        }
    }
}
