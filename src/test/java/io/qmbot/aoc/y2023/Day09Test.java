package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day09Test {
    Puzzle p = new Day09();

    String input = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45""";

    @Test
    public void part1(){
        Assertions.assertEquals(114, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(2, p.part2(input));
    }
}
