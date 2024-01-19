package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

public class Day10 implements Puzzle {
    @Override
    public Object part1(String input) {
        char[][] sketch = parse(input);
        Point start = start(sketch);
        return howLong(sketch, start) / 2;
    }

    @Override
    public Object part2(String input) {
        return null;
    }


    int howLong(char[][] sketch, Point start) {
        int y = start.y;
        int x = start.x;
        Point[] directions = {
                new Point(y + Direction.UP.deltaY, x + Direction.UP.deltaX, Direction.UP),
                new Point(y + Direction.RIGHT.deltaY, x + Direction.RIGHT.deltaX, Direction.RIGHT),
                new Point(y + Direction.DOWN.deltaY, x + Direction.DOWN.deltaX, Direction.DOWN),
                new Point(y + Direction.LEFT.deltaY, x + Direction.LEFT.deltaX, Direction.LEFT)
        };
        for (Point point : directions) {
            int newY = point.y;
            int newX = point.x;
            if (isValidPosition(newY, newX, sketch) && isValidMove(point.from, sketch[newY][newX])) {
                return destination(point, sketch, start);
            }
        }
        return 0;
    }

    boolean isValidPosition(int y, int x, char[][] sketch) {
        return y >= 0 && y < sketch.length && x >= 0 && x < sketch[0].length;
    }

    boolean isValidMove(Direction from, char next) {
        return (from == Direction.UP && (next == '|' || next == '7' || next == 'F')) ||
                (from == Direction.RIGHT && (next == '-' || next == '7' || next == 'J')) ||
                (from == Direction.DOWN && (next == '|' || next == 'L' || next == 'J')) ||
                (from == Direction.LEFT && (next == '-' || next == 'L' || next == 'F'));
    }

    int destination(Point point, char[][] sketch, Point start) {
        int steps = 1;
        Direction fromDirection;
        while (!(point.y == start.y && point.x == start.x)) {
            int y = point.y;
            int x = point.x;
            steps++;
            fromDirection = point.from;
            switch (sketch[y][x]) {
                case '|' -> move(point, fromDirection == Direction.UP ? Direction.UP : Direction.DOWN);
                case '-' -> move(point, fromDirection == Direction.LEFT ? Direction.LEFT : Direction.RIGHT);
                case 'L' -> move(point, fromDirection == Direction.DOWN ? Direction.RIGHT : Direction.UP);
                case 'J' -> move(point, fromDirection == Direction.DOWN ? Direction.LEFT : Direction.UP);
                case '7' -> move(point, fromDirection == Direction.RIGHT ? Direction.DOWN : Direction.LEFT);
                case 'F' -> move(point, fromDirection == Direction.LEFT ? Direction.DOWN : Direction.RIGHT);
                default -> {
                }
            }
        }
        return steps;
    }

    void move(Point p, Direction d) {
        p.y = p.y + d.deltaY;
        p.x = p.x + d.deltaX;
        p.from = d;
    }

    Point start(char[][] sketch) {
        for (int y = 0; y < sketch.length; y++) {
            for (int x = 0; x < sketch[0].length; x++) {
                if (sketch[y][x] == 'S') return new Point(y, x, null);
            }
        }
        return new Point(0, 0, null);
    }

    char[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        char[][] sketch = new char[sizeY][strings[0].length()];
        for (int y = 0; y < sizeY; y++) sketch[y] = strings[y].toCharArray();
        return sketch;
    }

    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final int deltaX;
        private final int deltaY;

        Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }

    static class Point {
        int y;
        int x;
        Direction from;

        public Point(int y, int x, Direction from) {
            this.y = y;
            this.x = x;
            this.from = from;
        }

        @Override
        public String toString() {
            return y + ", " + x;
        }
    }
}
