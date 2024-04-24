package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day19Test {
    Puzzle p = new Day19();
    String input = """
            px{a<2006:qkq,m>2090:A,rfg}
            pv{a>1716:R,A}
            lnx{m>1548:A,A}
            rfg{s<537:gd,x>2440:R,A}
            qs{s>3448:A,lnx}
            qkq{x<1416:A,crn}
            crn{x>2662:A,R}
            in{s<1351:px,qqz}
            qqz{s>2770:qs,m<1801:hdj,R}
            gd{a>3333:R,R}
            hdj{m>838:A,pv}

            {x=787,m=2655,a=1222,s=2876}
            {x=1679,m=44,a=2067,s=496}
            {x=2036,m=264,a=79,s=2244}
            {x=2461,m=1339,a=466,s=291}
            {x=2127,m=1623,a=2188,s=1013}""";

    String input2 = """
            cd{m<4000:R,A}
            ab{a<4000:R,cd}
            crn{x<4000:R,ab}
            in{s<4000:R,crn}

            {x=787,m=2655,a=1222,s=2876}
            """;

    String input3 = """
            cd{m>1:R,A}
            ab{a>1:R,cd}
            crn{x>1:R,ab}
            in{s>1:R,crn}

            {x=787,m=2655,a=1222,s=2876}
            """;

    String input4 = """
            cd{m>2:R,A}
            ab{a>1:R,cd}
            crn{x>1:R,ab}
            in{s>1:R,crn}

            {x=787,m=2655,a=1222,s=2876}
            """;

    String input5 = """
            cd{m>1:R,A}
            ab{a>1:R,cd}
            crn{x>1:R,ab}
            in{s>3999:three,s>1:R,crn}
            one{m<4000:R,A}
            two{a<4000:R,one}
            three{x<4000:R,two}

            {x=787,m=2655,a=1222,s=2876}
            """;

    String input6 = """
            px{a<2:qkq,m>9:A,rfg}
            pv{a>2:R,A}
            rfg{s<9:R,x>1:R,A}
            qkq{x<2:A,crn}
            crn{x>9:A,R}
            in{s<7:px,qqz}
            qqz{s>5:A,m<2:hdj,R}
            hdj{m>0:A,pv}

            {x=787,m=2655,a=1222,s=2876}""";

    String input7 = """
            cd{m>1:R,A}
            ab{a>1:R,cd}
            crn{x>1:R,ab}
            in{s>3999:three,s>1:R,crn}
            one{m<4000:A,A}
            two{a<4000:R,one}
            three{x<4000:R,two}

            {x=787,m=2655,a=1222,s=2876}
            """;

    @Test
    public void part1(){
        Assertions.assertEquals(19114, p.part1(input));
    }

    @Test
    void part2(){
        Assertions.assertEquals(167409079868000L, p.part2(input));
    }
    @Test
    void test(){
        Assertions.assertEquals(16000L, p.part2(input2));
    }
    @Test
    void test2(){
        Assertions.assertEquals(4L, p.part2(input3));
    }

    @Test
    void test3(){
        Assertions.assertEquals(9L, p.part2(input4));
    }
    @Test
    void test4(){
        Assertions.assertEquals(16004L, p.part2(input5));
    }
    @Test
    void test5(){
        Assertions.assertEquals(16004L, p.part2(input6));
    }
}
