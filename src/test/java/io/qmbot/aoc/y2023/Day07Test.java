package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day07Test {
    Puzzle p = new Day07();

    String input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483""";

    @Test
    public void part1(){
        Assertions.assertEquals(6440, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(5905, p.part2(input));
    }
}
