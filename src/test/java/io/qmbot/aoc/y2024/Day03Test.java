package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day03Test {
    Puzzle p = new Day03();

    String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    String input2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

    @Test
    public void part1(){
        Assertions.assertEquals(161, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(48, p.part2(input2));
    }
}
