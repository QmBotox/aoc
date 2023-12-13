package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.qmbot.aoc.y2023.Day12.spring;

public class Day12Test {
    Puzzle p = new Day12();

    String input = """
            #.#.### 1,1,3
            .#...#....###. 1,1,3
            .#.###.#.###### 1,3,1,6
            ####.#...#... 4,1,1
            #....######..#####. 1,6,5
            .###.##....# 3,2,1""";

    String input2 = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1""";

    @Test
    public void part1(){
        Assertions.assertEquals(21, p.part1(input2));
    }

    @Test
    void part2(){
        Assertions.assertEquals(525152, p.part2(input2));
    }

    @Test
    void trueForSpringTest() {
        List<Day12.Spring> springs = new ArrayList<>();
        for (String string : input.split("\n")){
            springs.add(spring(string));
        }
        for (Day12.Spring s : springs) {
            if (s.trueForString(s.springs)) {System.out.println("true");} else System.out.println("false");
        }
    }
}
