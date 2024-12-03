package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day02Test {
    Puzzle p = new Day02();

    String input = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9""";

    @Test
    public void part1(){
        Assertions.assertEquals(2L, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(4L, p.part2(input));
    }
}
