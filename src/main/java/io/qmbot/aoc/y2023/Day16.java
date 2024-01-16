package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

public class Day16 implements Puzzle {
    @Override
    public Object part1(String input) {
        Place[][] square = parse(input);
        boolean[][] energy = booleanArray(square);

        beam(Direction.RIGHT, new Point(0, 0), square, energy);
        int result = 0;
        for (int y = 0; y < square.length; y++) {
            for (int x = 0; x < square[0].length; x++) {
                if (energy[y][x]) result++;
            }
        }
        return result;
    }

    @Override
    public Object part2(String input) {
        return null;
    }

    Place[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        int sizeX = strings[0].length();
        Place[][] square = new Place[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                square[y][x] = new Place(strings[y].charAt(x));
            }
        }
        return square;
    }

    boolean[][] booleanArray(Place[][] square) {
        int sizeY = square.length;
        int sizeX = square[0].length;
        boolean[][] energy = new boolean[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                energy[y][x] = false;
            }
        }
        return energy;
    }

    void beam(Direction direction, Point now, Place[][] square, boolean[][] energy) {
        int breakLine = -1;
        int y = now.y;
        int x = now.x;
        energy[y][x] = true;
        y += direction.deltaY;
        x += direction.deltaX;

        while (y > -1 && y < square.length && x > -1 && x < square[0].length) {
            if (square[y][x].c == '.') {
                energy[y][x] = true;
                y += direction.deltaY;
                x += direction.deltaX;
            } else {
                switch (square[y][x].c) {
                    case '\\' -> {
                        switch (direction) {
                            case UP -> {
                                if (!square[y][x].isBeenUp) {
                                    square[y][x].isBeenUp = true;
                                    beam(Direction.LEFT, new Point(y, x), square, energy);
                                } else y = breakLine;
                            }
                            case DOWN -> {
                                if (!square[y][x].isBeenDown) {
                                    square[y][x].isBeenDown = true;
                                    beam(Direction.RIGHT, new Point(y, x), square, energy);
                                } else y = breakLine;
                            }
                            case LEFT -> {
                                if (!square[y][x].isBeenLeft) {
                                    square[y][x].isBeenLeft = true;
                                    beam(Direction.UP, new Point(y, x), square, energy);
                                } else y = breakLine;
                            }
                            case RIGHT -> {
                                if (!square[y][x].isBeenRight) {
                                    square[y][x].isBeenRight = true;
                                    beam(Direction.DOWN, new Point(y, x), square, energy);
                                } else y = breakLine;
                            }
                        }
                    }
                    case '/' -> {
                        switch (direction) {
                            case UP -> {
                                if (!square[y][x].isBeenUp) {
                                    square[y][x].isBeenUp = true;
                                    beam(Direction.RIGHT, new Point(y, x), square, energy);
                                } y = breakLine;
                            }
                            case DOWN -> {
                                if (!square[y][x].isBeenDown) {
                                    square[y][x].isBeenDown = true;
                                    beam(Direction.LEFT, new Point(y, x), square, energy);
                                } y = breakLine;
                            }
                            case LEFT -> {
                                if (!square[y][x].isBeenLeft) {
                                    square[y][x].isBeenLeft = true;
                                    beam(Direction.DOWN, new Point(y, x), square, energy);
                                } y = breakLine;
                            }
                            case RIGHT -> {
                                if (!square[y][x].isBeenRight) {
                                    square[y][x].isBeenRight = true;
                                    beam(Direction.UP, new Point(y, x), square, energy);
                                } y = breakLine;
                            }
                        }
                    }
                    case '|' -> {
                        switch (direction) {
                            case LEFT, RIGHT -> {
                                if (!square[y][x].isBeenLeft || !square[y][x].isBeenRight) {
                                    square[y][x].isBeenRight = true;
                                    square[y][x].isBeenLeft = true;
                                    beam(Direction.DOWN, new Point(y, x), square, energy);
                                    beam(Direction.UP, new Point(y, x), square, energy);
                                } else y = breakLine;
                            }
                            default -> {
                                energy[y][x] = true;
                                y += direction.deltaY;
                                x += direction.deltaX;
                            }
                        }
                    }
                    case '-' -> {
                        switch (direction) {
                            case DOWN, UP -> {
                                if (!square[y][x].isBeenDown || !square[y][x].isBeenUp) {
                                    square[y][x].isBeenUp = true;
                                    square[y][x].isBeenDown = true;
                                    beam(Direction.LEFT, new Point(y, x), square, energy);
                                    beam(Direction.RIGHT, new Point(y, x), square, energy);
                                } else y = breakLine;
                            }
                            default -> {
                                energy[y][x] = true;
                                y += direction.deltaY;
                                x += direction.deltaX;
                            }
                        }
                    }
                }
            }
        }
    }

    class Place {
        char c;
        boolean isBeenUp = false;
        boolean isBeenDown = false;
        boolean isBeenLeft = false;
        boolean isBeenRight = false;

        public Place(char c) {
            this.c = c;
        }
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

    class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
