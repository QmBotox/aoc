package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Test;

import static io.qmbot.aoc.y2023.Day12.countOfSolutions;
import static io.qmbot.aoc.y2023.Day12.spring;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(21L, p.part1(input2));
    }

    @Test
    void part2(){
        assertEquals(525152L, p.part2(input2));
    }

    @Test
    void count1Test() {
        String str = "?###???????? 3,2,1";
        Day12.Spring spr = spring(str);

        System.out.println(countOfSolutions(spr));
    }

    @Test
    void countOfSolutionsTest() {
        assertEquals(1L, countOfSolutions(spring("# 1")));
        assertEquals(1L, countOfSolutions(spring("? 1")));
        assertEquals(1L, countOfSolutions(spring("#. 1")));
        assertEquals(2L, countOfSolutions(spring("?? 1")));
        assertEquals(2L, countOfSolutions(spring(".?? 1")));
        assertEquals(2L, countOfSolutions(spring("??. 1")));
    }
}
