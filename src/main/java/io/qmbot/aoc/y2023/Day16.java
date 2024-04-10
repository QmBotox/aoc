package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;

public class Day16 implements Puzzle {
    @Override
    public Object part1(String input) {
        Place[][] square = parse(input);
        beam(Direction.RIGHT, new Point(0, -1), square);
        return max(0, square);
    }

    @Override
    public Object part2(String input) {
        Place[][] square = parse(input);
        int max = 0;
        for (int i = 0; i < square.length; i++) {
            falseMaker(square);
            beam(Direction.RIGHT, new Point(i, -1), square);
            max = max(max, square);
            falseMaker(square);
            beam(Direction.LEFT, new Point(i, square[0].length), square);
            max = max(max, square);
        }
        for (int i = 0; i < square[0].length; i++) {
            falseMaker(square);
            beam(Direction.DOWN, new Point(-1, i), square);
            max = max(max, square);
            falseMaker(square);
            beam(Direction.UP, new Point(square.length, i), square);
            max = max(max, square);
        }
        return max;
    }

    void falseMaker(Place[][] square) {
        for (Place[] places : square) {
            for (int x = 0; x < square[0].length; x++) {
                places[x].isBeenUp = false;
                places[x].isBeenDown = false;
                places[x].isBeenRight = false;
                places[x].isBeenLeft = false;
                places[x].isBeen = false;
            }
        }
    }

    int max(int max, Place[][] square) {
        int result = 0;
        for (Place[] places : square) {
            for (int x = 0; x < square[0].length; x++) {
                if (places[x].isBeen) result++;
            }
        }
        if (result > max) max = result;
        return max;
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

    void beam(Direction direction, Point now, Place[][] square) {
        int breakLine = -1;
        int y = now.y;
        int x = now.x;
        y += direction.deltaY;
        x += direction.deltaX;
        while (y > -1 && y < square.length && x > -1 && x < square[0].length) {
            square[y][x].isBeen = true;
            if (square[y][x].c == '.') {
                y += direction.deltaY;
                x += direction.deltaX;
            } else {
                switch (square[y][x].c) {
                    case '\\' -> {
                        switch (direction) {
                            case UP -> {
                                if (!square[y][x].isBeenUp) {
                                    square[y][x].isBeenUp = true;
                                    beam(Direction.LEFT, new Point(y, x), square);
                                } else y = breakLine;
                            }
                            case DOWN -> {
                                if (!square[y][x].isBeenDown) {
                                    square[y][x].isBeenDown = true;
                                    beam(Direction.RIGHT, new Point(y, x), square);
                                } else y = breakLine;
                            }
                            case LEFT -> {
                                if (!square[y][x].isBeenLeft) {
                                    square[y][x].isBeenLeft = true;
                                    beam(Direction.UP, new Point(y, x), square);
                                } else y = breakLine;
                            }
                            case RIGHT -> {
                                if (!square[y][x].isBeenRight) {
                                    square[y][x].isBeenRight = true;
                                    beam(Direction.DOWN, new Point(y, x), square);
                                } else y = breakLine;
                            }
                        }
                    }
                    case '/' -> {
                        switch (direction) {
                            case UP -> {
                                if (!square[y][x].isBeenUp) {
                                    square[y][x].isBeenUp = true;
                                    beam(Direction.RIGHT, new Point(y, x), square);
                                }
                                y = breakLine;
                            }
                            case DOWN -> {
                                if (!square[y][x].isBeenDown) {
                                    square[y][x].isBeenDown = true;
                                    beam(Direction.LEFT, new Point(y, x), square);
                                }
                                y = breakLine;
                            }
                            case LEFT -> {
                                if (!square[y][x].isBeenLeft) {
                                    square[y][x].isBeenLeft = true;
                                    beam(Direction.DOWN, new Point(y, x), square);
                                }
                                y = breakLine;
                            }
                            case RIGHT -> {
                                if (!square[y][x].isBeenRight) {
                                    square[y][x].isBeenRight = true;
                                    beam(Direction.UP, new Point(y, x), square);
                                }
                                y = breakLine;
                            }
                        }
                    }
                    case '|' -> {
                        switch (direction) {
                            case LEFT, RIGHT -> {
                                if (!square[y][x].isBeenLeft || !square[y][x].isBeenRight) {
                                    square[y][x].isBeenRight = true;
                                    square[y][x].isBeenLeft = true;
                                    beam(Direction.DOWN, new Point(y, x), square);
                                    beam(Direction.UP, new Point(y, x), square);
                                } else y = breakLine;
                            }
                            default -> {
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
                                    beam(Direction.LEFT, new Point(y, x), square);
                                    beam(Direction.RIGHT, new Point(y, x), square);
                                } else y = breakLine;
                            }
                            default -> {
                                y += direction.deltaY;
                                x += direction.deltaX;
                            }
                        }
                    }
                }
            }
        }
    }

    static class Place {
        char c;
        boolean isBeen = false;
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

        final int deltaX;
        final int deltaY;

        Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }
}
