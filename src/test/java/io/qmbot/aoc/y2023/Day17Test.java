package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day17Test {
    Puzzle p = new Day17();

    String input = """
            2413432311323
            3215453535623
            3255245654254
            3446585845452
            4546657867536
            1438598798454
            4457876987766
            3637877979653
            4654967986887
            4564679986453
            1224686865563
            2546548887735
            4322674655533""";

    @Test
    public void part1(){
        Assertions.assertEquals(46, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(400, p.part2(input));
    }
}
