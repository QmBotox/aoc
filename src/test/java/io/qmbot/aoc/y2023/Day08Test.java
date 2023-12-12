package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.qmbot.aoc.y2023.Day08.gcd;
import static io.qmbot.aoc.y2023.Day08.lcm;

public class Day08Test {
    Puzzle p = new Day08();

    String input = """
            RL

            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)""";

    String input2 = """
            LLR

            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)""";

    String input3 = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)""";

    @Test
    public void part1First(){
        Assertions.assertEquals(2, p.part1(input));
    }
    @Test
    public void part1Second(){
        Assertions.assertEquals(6, p.part1(input2));
    }

    @Test
    void part2(){
        Assertions.assertEquals(6L, p.part2(input2));
    }
    @Test
    void lcmTest(){
        System.out.println(lcm(7L, 13L));
    }

    @Test
    void gcdTest(){
        System.out.println(gcd(7L, 13L));
    }
}
