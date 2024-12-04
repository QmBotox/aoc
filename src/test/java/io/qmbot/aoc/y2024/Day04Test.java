package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day04Test {
    Puzzle p = new Day04();

    String input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX""";

    @Test
    public void part1() {
        Assertions.assertEquals(18, p.part1(input));
    }

    @Test
    void part2() {
        Assertions.assertEquals(9, p.part2(input));
    }
}
