package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day15Test {
    Puzzle p = new Day15();

    String input = "HASH";
    String input2 = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

    String i = "rn";

    @Test
    public void hash(){
        Assertions.assertEquals(52L, p.part1(input));
    }
    @Test
    public void hash2(){
        Assertions.assertEquals(0L, p.part1(i));
    }
    @Test
    public void part1(){
        Assertions.assertEquals(1320L, p.part1(input2));
    }

    @Test
    void part2(){
        Assertions.assertEquals(145L, p.part2(input2));
    }
}
