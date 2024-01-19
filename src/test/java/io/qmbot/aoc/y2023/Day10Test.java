package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day10Test {
    Puzzle p = new Day10();

    String input = """
            -L|F7
            7S-7|
            L|7||
            -L-J|
            L|-JF""";

    String input2 = """
            7-F7-
            .FJ|7
            SJLL7
            |F--J
            LJ.LJ""";

    @Test
    public void part1F(){
        Assertions.assertEquals(4, p.part1(input));
    }
    @Test
    public void part1S(){
        Assertions.assertEquals(8, p.part1(input2));
    }

    @Test
    void part2(){
        Assertions.assertEquals(2, p.part2(input));
    }
}
