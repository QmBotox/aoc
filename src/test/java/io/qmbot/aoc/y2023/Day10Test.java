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

    String input3 = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........""";

    String input4 = """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...""";

    String input5 = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L""";
    @Test
    public void part1F(){
        Assertions.assertEquals(4, p.part1(input));
    }
    @Test
    public void part1S(){
        Assertions.assertEquals(8, p.part1(input2));
    }
    @Test
    public void S(){
        Assertions.assertEquals(1, p.part2(input));
    }
    @Test
    void part2Z() {Assertions.assertEquals(1, p.part2(input));}
    @Test
    void part2F(){
        Assertions.assertEquals(4, p.part2(input3));
    }
    @Test
    void part2S(){
        Assertions.assertEquals(8, p.part2(input4));
    }
    @Test
    void part2T(){
        Assertions.assertEquals(10, p.part2(input5));
    }
}
