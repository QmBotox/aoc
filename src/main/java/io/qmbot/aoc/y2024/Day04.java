package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day04 implements Puzzle {
    @Override
    public Object part1(String input) {
        String[] parse = input.split(REGEX_NEW_LINE);
        return Arrays.stream(parse).flatMapToInt(row -> IntStream.range(0, row.length())
                        .filter(x -> row.charAt(x) == 'X')
                        .map(x -> countWord(parse, Arrays.asList(parse).indexOf(row), x)))
                .sum();

    }

    @Override
    public Object part2(String input) {
        String[] parse = input.split(REGEX_NEW_LINE);
        int result = 0;
        for(int y = 0; y < parse.length; y++) {
            for(int x = 0; x < parse[0].length(); x++) {
                int finalY = y;
                int finalX = x;
                if (parse[y].charAt(x) == 'A' &&
                        Stream.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT)
                                .allMatch(direction -> isValid(parse, finalY, finalX, direction, 2)) &&
                        isX(parse, y, x)) {
                    result++;
                }
            }
        }
        return result;
    }

    int countWord(String[] parse, int y, int x) {
        return (int) Arrays.stream(Direction.values())
                .filter(direction -> isValid(parse, y, x, direction, "XMAS".length()) && checkXmas(parse, y, x, direction)).count();
    }

    Boolean isX(String[] parse, int y, int x) {
        char upLeft = parse[y + Direction.UP_LEFT.deltaY].charAt(x + Direction.UP_LEFT.deltaX);
        char downRight = parse[y + Direction.DOWN_RIGHT.deltaY].charAt(x + Direction.DOWN_RIGHT.deltaX);
        char downLeft = parse[y + Direction.DOWN_LEFT.deltaY].charAt(x + Direction.DOWN_LEFT.deltaX);
        char upRight = parse[y + Direction.UP_RIGHT.deltaY].charAt(x + Direction.UP_RIGHT.deltaX);

        if ((upLeft == 'M' && downRight == 'S') || (upLeft == 'S' && downRight == 'M')) {
            return (downLeft == 'M' && upRight == 'S') || (downLeft == 'S' && upRight == 'M');
        }

        return false;
    }

    private boolean isValid(String[] parse, int y, int x, Direction direction, int wordLength) {
        wordLength = wordLength-1;
        return y + wordLength * direction.getDeltaY() >= 0
                && y + wordLength * direction.getDeltaY() < parse.length
                && x + wordLength * direction.getDeltaX() >= 0
                && x + wordLength * direction.getDeltaX() < parse[0].length();
    }

    private boolean checkXmas(String[] parse, int y, int x, Direction direction) {
        String word = "XMAS";
        int wordLength = word.length();
        for (int i = 0; i < wordLength; i++) {
            if (parse[y + i * direction.getDeltaY()].charAt(x + i * direction.getDeltaX()) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP_LEFT(-1, -1),
        UP_RIGHT(1, -1),
        DOWN_LEFT(-1, 1),
        DOWN_RIGHT(1, 1);

        private final int deltaX;
        private final int deltaY;

        Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public int getDeltaX() {
            return deltaX;
        }

        public int getDeltaY() {
            return deltaY;
        }
    }

}
