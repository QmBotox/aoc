package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.qmbot.aoc.y2023.Day18.*;

public class Day18Test {
    Puzzle p = new Day18();

    String input = """
            R 6 (#70c710)
            D 5 (#0dc571)
            L 2 (#5713f0)
            D 2 (#d2c081)
            R 2 (#59c680)
            D 2 (#411b91)
            L 5 (#8ceee2)
            U 2 (#caa173)
            L 1 (#1b58a2)
            U 2 (#caa171)
            R 2 (#7807d2)
            U 3 (#a77fa3)
            L 2 (#015232)
            U 2 (#7a21e3)""";

    String input2 = """
            R 2 (#70c710)
            D 2 (#0dc571)
            L 2 (#5713f0)
            U 2 (#caa173)
            """;

    @Test
    public void part1(){
        Assertions.assertEquals(62L, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(952408144115L, p.part2(input));
    }

    @Test
    void shoelaceAreaTest(){
        List<Day11.Point> angles = new ArrayList<>();
        AtomicInteger ai = new AtomicInteger(0);
        int m = perimeter(input2, angles, true);
        System.out.println(shoelaceArea(angles));
        System.out.println(countInside(ai.get(), angles));
    }
}
