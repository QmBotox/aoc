package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day16Test {
    Puzzle p = new Day14();

    String input = """
            .|...\\....
            |.-.\\.....
            .....|-...
            ........|.
            ..........
            .........\\
            ..../.\\\\..
            .-.-/..|..
            .|....-|.\\
            ..//.|....""";

    @Test
    public void part1(){
        Assertions.assertEquals(136, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(400, p.part2(input));
    }
}
