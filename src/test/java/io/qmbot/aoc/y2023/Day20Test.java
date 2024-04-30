package io.qmbot.aoc.y2023;

import java.util.Collections;
import java.util.Map;
import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day20Test {
    Puzzle p = new Day20();

    String input1 = """
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
            &con -> output""";

    @Test
    public void part1() {
        Assertions.assertEquals(32000000L, p.part1(input1));
    }

    @Test
    public void input1() {
        Assertions.assertEquals(62L, p.part1(input1));
    }


    @Test
    public void input2() {
        Map<String, Day20.Module> map = Day20.parse(input2);
        map.put("output", new Day20.Broadcaster(Collections.emptyList()));
        Day20.pushButton(map);
        Day20.pushButton(map);
        Day20.pushButton(map);
        Day20.pushButton(map);
    }

//    @Test
//    void part2() {
//        Assertions.assertEquals(952408144115L, p.part2(input1));
//    }
}
