package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
    void bfsTest() {
        char[][] parse = Day21.parse(input);
        Map<Day14.Point, Integer> bfs = Day21.bfs(Day21.start(parse), parse);
        int i = 0;
    }
}
