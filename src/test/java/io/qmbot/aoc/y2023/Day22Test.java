package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day22Test {
    Puzzle p = new Day22();

    String input = """
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9""";

    String input2 = """
            0,0,1~0,1,1
            1,1,1~1,1,1
            0,0,2~0,0,2
            0,1,2~1,1,2""";

    String input3 = """
            0,0,1~1,0,1
            0,1,1~0,1,2
            0,0,5~0,0,5
            0,0,4~0,1,4""";

    String input4 = """
            1,0,1~1,2,1
            1,1,2~1,3,2""";

    String input5 = """
            0,0,1~0,0,1
            1,1,1~1,1,1
            0,0,2~0,1,2
            0,1,3~1,1,3""";

    @Test
    public void part1() {
        Assertions.assertEquals(5, p.part1(input));
    }

    @Test
    public void part1one() {
        Assertions.assertEquals(3, p.part1(input2));
    }

    @Test
    public void part1two() {
        Assertions.assertEquals(2, p.part1(input3));
    }

    @Test
    public void part1three() {
        Assertions.assertEquals(1, p.part1(input4));
    }

    @Test
    public void part1four() {
        Assertions.assertEquals(2, p.part1(input5));
    }

    @Test
    void part2() {
        Assertions.assertEquals(7, p.part2(input));
    }
}
