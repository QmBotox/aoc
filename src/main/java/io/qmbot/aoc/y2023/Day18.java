package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day11.Point;

import java.util.ArrayList;
import java.util.List;

public class Day18 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Point> angles = new ArrayList<>();
        return countInside(perimeter(input, angles, true), angles);
    }

    @Override
    public Object part2(String input) {
        List<Point> angles = new ArrayList<>();
        return countInside(perimeter(input, angles, false), angles);
    }


    static int perimeter(String input, List<Point> angles, boolean part1) {
        int perimeter = 0;
        int n;
        char c;
        Point p = new Point(0, 0);
        for (String s : input.split(REGEX_NEW_LINE)) {
            angles.add(p);
            long y = p.y;
            long x = p.x;
            if (part1) {
                n = Integer.parseInt(s.split(" ")[1]);
                c = s.charAt(0);
            } else {
                s = s.split(" ")[2];
                n = Integer.parseInt(s.substring(2, 7), 16);
                c = s.charAt(7);
            }
            perimeter += n;
            switch (c) {
                case 'U', '3' -> y -= n;
                case 'R', '0' -> x += n;
                case 'D', '1' -> y += n;
                case 'L', '2' -> x -= n;
            }
            p = new Point(y, x);
        }
        return perimeter;
    }
    static double shoelaceArea(List<Point> angles) {
        int n = angles.size();
        double a = 0.0;
        for (int i = 0; i < n - 1; i++) {
            a += angles.get(i).x * angles.get(i + 1).y - angles.get(i + 1).x * angles.get(i).y;
        }
        return Math.abs(a + angles.get(n - 1).x * angles.get(0).y - angles.get(0).x * angles.get(n - 1).y) / 2.0;
    }

    static long countInside(long g, List<Point> angles) {
        return (long) (shoelaceArea(angles) + (g / 2) + 1);
    }
}
