package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day01Test {
    Puzzle p = new Day01();

    String input = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3""";

    @Test
    public void part1(){
        Assertions.assertEquals(11, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(31, p.part2(input));
    }
}
