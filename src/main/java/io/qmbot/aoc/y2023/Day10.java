package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 implements Puzzle {
    @Override
    public Object part1(String input) {
        char[][] sketch = parse(input);
        long[][] steps = new long[sketch.length][sketch[0].length];
        Point start = start(sketch, steps);
        List<Point> neighbors = new ArrayList<>();
        neighbors.add(start);
        while (neighbors.size() > 0) {
            List<Point> newNeighbors = new ArrayList<>();
            for (Point neighbor : neighbors) {
                newNeighbors.addAll(neighbors(neighbor, sketch, steps));
            }
            neighbors.clear();
            neighbors.addAll(newNeighbors);
        }
        return maxStep(steps) - 1;
    }

    @Override
    public Object part2(String input) {
        return null;
    }


    List<Point> neighbors(Point point, char[][] sketch, long[][] steps) {
        List<Point> neighbors = new ArrayList<>();
        int y = point.y;
        int x = point.x;
        long step = steps[y][x] + 1;
        Point up = new Point(y - 1, x);
        Point right = new Point(y, x + 1);
        Point down = new Point(y + 1, x);
        Point left = new Point(y, x - 1);
        if (sketch[y][x] == 'S') {
            if (up.y >= 0 ) {
                neighbors.add(up);
                steps[y - 1][x] = step;
            }
            if (right.x < sketch[0].length) {
                neighbors.add(right);
                steps[y][x + 1] = step;
            }
            if (down.y < sketch.length) {
                neighbors.add(down);
                steps[y + 1][x] = step;
            }
            if (left.x >= 0) {
                neighbors.add(left);
                steps[y][x - 1] = step;
            }
        }
        if (up.y >= 0 && isConnecting(point, up, sketch) && setStep(steps, up, (int) step)) {
            neighbors.add(up);
        }
        if (right.x < sketch[0].length && isConnecting(point, right, sketch) && setStep(steps, right, (int) step)) {
            neighbors.add(right);
        }
        if (down.y < sketch.length && isConnecting(point, down, sketch)  && setStep(steps, down, (int) step)) {
            neighbors.add(down);
        }
        if (left.x >= 0 && isConnecting(point, left, sketch)  && setStep(steps, left, (int) step)) {
            neighbors.add(left);
        }
        return neighbors;
    }

    static  boolean setStep(long[][] steps, Point point, int step) {
        if (steps[point.y][point.x] == 0) {
            steps[point.y][point.x] = step;
            return  true;
        }
        return false;
    }

    boolean isConnecting(Point now, Point neighbor, char[][] sketch) {
        switch (sketch[now.y][now.x]) {
            case '|' -> {
                if ((neighbor.y == now.y - 1 &&
                        (sketch[neighbor.y][neighbor.x] == '|' || sketch[neighbor.y][neighbor.x] == 'F'
                                || sketch[neighbor.y][neighbor.x] == '7'))
                        || (neighbor.y == now.y + 1 &&
                        (sketch[neighbor.y][neighbor.x] == '|' || sketch[neighbor.y][neighbor.x] == 'J'
                                || sketch[neighbor.y][neighbor.x] == 'L'))) return true;
            }
            case '-' -> {
                if ((neighbor.x == now.x - 1 &&
                        (sketch[neighbor.y][neighbor.x] == '-' || sketch[neighbor.y][neighbor.x] == 'F'
                                || sketch[neighbor.y][neighbor.x] == 'L'))
                        || (neighbor.x == now.x + 1 &&
                        (sketch[neighbor.y][neighbor.x] == '-' || sketch[neighbor.y][neighbor.x] == 'J'
                                || sketch[neighbor.y][neighbor.x] == '7'))) return true;
            }
            case 'L' -> {
                if ((neighbor.x == now.x + 1 &&
                        (sketch[neighbor.y][neighbor.x] == '-' || sketch[neighbor.y][neighbor.x] == '7'
                                || sketch[neighbor.y][neighbor.x] == 'J'))
                        || (neighbor.y == now.y - 1 &&
                        (sketch[neighbor.y][neighbor.x] == '|' || sketch[neighbor.y][neighbor.x] == '7'
                                || sketch[neighbor.y][neighbor.x] == 'F'))) return true;
            }
            case 'J' -> {
                if ((neighbor.x == now.x - 1 &&
                        (sketch[neighbor.y][neighbor.x] == '-' || sketch[neighbor.y][neighbor.x] == 'F'
                                || sketch[neighbor.y][neighbor.x] == 'L'))
                        || (neighbor.y == now.y - 1 &&
                        (sketch[neighbor.y][neighbor.x] == '|' || sketch[neighbor.y][neighbor.x] == '7'
                                || sketch[neighbor.y][neighbor.x] == 'F'))) return true;
            }
            case '7' -> {
                if ((neighbor.x == now.x - 1 &&
                        (sketch[neighbor.y][neighbor.x] == '-' || sketch[neighbor.y][neighbor.x] == 'F'
                                || sketch[neighbor.y][neighbor.x] == 'L'))
                        || (neighbor.y == now.y + 1 &&
                        (sketch[neighbor.y][neighbor.x] == '|' || sketch[neighbor.y][neighbor.x] == 'J'
                                || sketch[neighbor.y][neighbor.x] == 'L'))) return true;
            }
            case 'F' -> {
                if ((neighbor.x == now.x + 1 &&
                        (sketch[neighbor.y][neighbor.x] == '-' || sketch[neighbor.y][neighbor.x] == '7'
                                || sketch[neighbor.y][neighbor.x] == 'J'))
                        || (neighbor.y == now.y + 1 &&
                        (sketch[neighbor.y][neighbor.x] == '|' || sketch[neighbor.y][neighbor.x] == 'J'
                                || sketch[neighbor.y][neighbor.x] == 'L'))) return true;
            }
            default -> {;}
        }
        return false;
    }

    long maxStep(long[][] steps) {
        int sizeX = steps[0].length;
        long maxStep = 0;
        for (long[] step : steps) {
            for (int x = 0; x < sizeX; x++) {
                if (step[x] >= maxStep) {
                    maxStep = step[x];
                }
            }
        }
        return maxStep;
    }

    Point start(char[][] sketch, long[][] steps) {
        int sizeY = sketch.length;
        int sizeX = sketch[0].length;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (sketch[y][x] == 'S') {
                    steps[y][x] = 1;
                    return new Point(y, x);
                }
            }
        }
        return new Point(0, 0);
    }

    char[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        int sizeX = strings[0].length();
        char[][] sketch = new char[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                sketch[y][x] = strings[y].charAt(x);
            }
        }
        return sketch;
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return y + ", " + x;
        }
    }
}
