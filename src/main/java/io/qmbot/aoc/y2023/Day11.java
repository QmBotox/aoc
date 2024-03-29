package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Day11 implements Puzzle {
    @Override
    public Object part1(String input) {
        return allLength(parse(input), 2L);
    }

    @Override
    public Object part2(String input) {
        return allLength(parse(input), 1000000L);
    }

    long allLength (List<Point> galaxies, long spaceExpands) {
        spaceExpands -= 1;
        addSpace(galaxies, spaceExpands, g -> g.x, (g, s) -> g.x += s);
        addSpace(galaxies, spaceExpands, g -> g.y, (g, s) -> g.y += s);
        long size = galaxies.size();
        long result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                result += shortPath(galaxies.get(i), galaxies.get(j));
            }
        }
        return result;
    }

    void addSpace(List<Point> galaxies, long spaceExpands, Function<Point, Long> f, BiConsumer<Point, Long> bc) {
        for (long n = galaxies.stream().mapToLong(f::apply).max().orElseThrow(); n > -1; n--) {
            long target = n;
            if (galaxies.stream().noneMatch(galaxy -> f.apply(galaxy) == target)) {
                for (Point g : galaxies) {
                    if (f.apply(g) > n) bc.accept(g, spaceExpands);
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
        return Math.abs(first.y - second.y) + Math.abs(first.x - second.x);
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
