package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day11 implements Puzzle {
    @Override
    public Object part1(String input) {
        return allLength(parse(input), 2L);
    }

    @Override
    public Object part2(String input) {
        return allLength(parse(input), 1000000L);
    }

    long allLength (Galaxy galaxy, long spaceExpands) {
        spaceExpands -= 1;
        List<Point> galaxies = galaxy.galaxies;
        column(galaxy.maxX, galaxies, spaceExpands);
        rows(galaxy.maxY, galaxies, spaceExpands);
        long size = galaxies.size();
        long result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                result += shortPath(galaxies.get(i), galaxies.get(j));
            }
        }
        return result;
    }

    void column(long length, List<Point> galaxies, long spaceExpands) {
        for (long x = length; x > -1; x--) {
            long targetX = x;
            boolean haveGalaxy = galaxies.stream().anyMatch(galaxy -> galaxy.x == targetX);
            if (!haveGalaxy) {
                for (Point galaxy : galaxies) {
                    if (galaxy.x > x) galaxy.x += spaceExpands;
                }
            }
        }
    }

    void rows(long length, List<Point> galaxies, long spaceExpands) {
        for (long y = length - 1; y > -1; y--) {
            long targetY = y;
            boolean haveGalaxy = galaxies.stream().anyMatch(galaxy -> galaxy.y == targetY);
            if (!haveGalaxy) {
                for (Point galaxy : galaxies) {
                    if (galaxy.y > y) galaxy.y += spaceExpands;
                }
            }
        }
    }

    Galaxy parse(String input) {
        List<Point> galaxies = new ArrayList<>();
        String[] strings = input.split(REGEX_NEW_LINE);
        long maxY = 0;
        long maxX = 0;
        for (int y = 0; y < strings.length; y++) {
            for (int x = 0; x < strings[0].length(); x++) {
                if (strings[y].charAt(x) == '#') {
                    galaxies.add(new Point(y, x));
                    maxY = y > maxY ? y : maxY;
                    maxX = x > maxX ? x : maxX;
                }
            }
        }
        return new Galaxy(galaxies, maxY, maxX);
    }

    static long shortPath(Point first, Point second) {
        return Math.abs(first.y - second.y) + Math.abs(first.x - second.x);
    }
    static class Galaxy {
        List<Point> galaxies;
        long maxY;
        long maxX;

        public Galaxy(List<Point> galaxies, long maxY, long maxX) {
            this.galaxies = galaxies;
            this.maxY = maxY;
            this.maxX = maxX;
        }
    }

    static class Point {
        long y;
        long x;

        public Point(long y, long x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return   y + ", " + x ;
        }
    }
}
