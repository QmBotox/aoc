package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day21Test {
    Puzzle p = new Day21();

    String input = """
            ...........
            .....###.#.
            .###.##..#.
            ..#.#...#..
            ....#.#....
            .##..S####.
            .##..#...#.
            .......##..
            .##.#.####.
            .##..##.##.
            ...........""";

    @Test
    public void part1() {
        Assertions.assertEquals(16L, p.part1(input));
    }

    @Test
    void part2() {
        Assertions.assertEquals(64, p.part2(input));
    }
}
