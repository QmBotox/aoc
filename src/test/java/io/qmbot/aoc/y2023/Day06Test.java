package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day06Test {
    Puzzle p = new Day06();

    String input = """
            Time:      7  15   30
            Distance:  9  40  200""";

    @Test
    public void part1(){
        Assertions.assertEquals(288, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(71503, p.part2(input));
    }
}
