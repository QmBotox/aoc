package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 implements Puzzle {
    @Override
    public Object part1(String input) {
        NetworkMap networkMap = parse(input);
        String now = "AAA";
        String navigate = networkMap.navigate;
        int steps = 0;
        while (!now.equals("ZZZ")) {
            for (int i = 0; i < navigate.length(); i++) {
                now = now(navigate, i, networkMap.network, now);
                steps++;
                if (now.equals("ZZZ")) break;
            }
        }
        return steps;
    }

    @Override
    public Object part2(String input) {
        NetworkMap networkMap = parse(input);
        String navigate = networkMap.navigate;
        int steps;
        long result = 1;
        for (String now : networkMap.ways) {
            steps = 0;
            while (now.charAt(2) != 'Z') {
                for (int i = 0; i < navigate.length(); i++) {
                    now = now(navigate, i, networkMap.network, now);
                    steps++;
                }
            }
            result = lcm(result, steps);
        }
        return result;
    }

    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static String now(String navigate, int i, Map<String, LeftRight> network, String now) {
        switch (navigate.charAt(i)) {
            case 'L' -> {
                return network.get(now).left;
            }
            case 'R' -> {
                return network.get(now).right;
            }
            default -> throw new IllegalStateException("Unexpected value: " + navigate.charAt(i));
        }
    }

    public NetworkMap parse(String input) {
        String[] parse = input.split(REGEX_EMPTY_LINE);
        Map<String, LeftRight> network = new HashMap<>();
        List<String> ways = new ArrayList<>();
        for (String string : parse[1].split(REGEX_NEW_LINE)) {
            String[] words = string.split(" ");
            String name = words[0];
            if (name.charAt(name.length() - 1) == 'A') {
                ways.add(name);
            }
            network.put(name, new LeftRight(words[2].substring(1, 4), words[3].substring(0, 3)));
        }
        return new NetworkMap(parse[0], network, ways);
    }

    static class NetworkMap {
        String navigate;
        Map<String, LeftRight> network;
        List<String> ways;

        public NetworkMap(String navigate, Map<String, LeftRight> network, List<String> ways) {
            this.navigate = navigate;
            this.network = network;
            this.ways = ways;
        }
    }

    static class LeftRight {
        String left;
        String right;

        public LeftRight(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }
}
