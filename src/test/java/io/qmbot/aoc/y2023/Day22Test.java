package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day22Test {
    Puzzle p = new Day22();

    String input = """
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9""";

    @Test
    public void part1() {
        Assertions.assertEquals(5, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(167409079868000L, p.part2(input));
    }
}
