package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qmbot.aoc.y2023.Day11.shortPath;

public class Day11Test {
    static Puzzle p = new Day11();

    String input = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....""";

    @Test
    public void part1(){
        Assertions.assertEquals(374L, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(2, p.part2(input));
    }

    @Test
    void pathTest() {
        Day11.Point p1 = new Day11.Point(0, 4);
        Day11.Point p7 = new Day11.Point(10, 9);

        Day11.Point p3 = new Day11.Point(2, 0);
        Day11.Point p6 = new Day11.Point(7, 12);

        System.out.println(shortPath(p1, p7));
        System.out.println(shortPath(p3, p6));
    }

}
