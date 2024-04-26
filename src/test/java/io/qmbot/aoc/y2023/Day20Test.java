package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day20Test {
    Puzzle p = new Day20();

    String input = """
            broadcaster -> a, b, c
            %a -> b
            %b -> c
            %c -> inv
            &inv -> a""";
    String input2 = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output
            &output ->  """;

    @Test
    public void part1() {
        Assertions.assertEquals(62L, p.part1(input2));
    }

    @Test
    void part2() {
        Assertions.assertEquals(952408144115L, p.part2(input));
    }
}
