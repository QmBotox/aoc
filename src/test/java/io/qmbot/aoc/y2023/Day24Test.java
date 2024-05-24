package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day24Test {
    Puzzle p = new Day24();

    String input = """
            19, 13, 30 @ -2,  1, -2
            18, 19, 22 @ -1, -1, -2
            20, 25, 34 @ -2, -2, -4
            12, 31, 28 @ -1, -2, -1
            20, 19, 15 @  1, -5, -3""";

    @Test
    public void part1() {
        Assertions.assertEquals(2, p.part1(input));
    }

    @Test
    public void part2() {
        Assertions.assertEquals(154, p.part2(input));
    }
}
