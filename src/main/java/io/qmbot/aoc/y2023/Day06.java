package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day06 implements Puzzle {
    @Override
    public Object part1(String input) {
        Map<Integer, Integer> timeAndDistance = parse(input);
        int waysToWin = 1;
        for (Map.Entry<Integer, Integer> race : timeAndDistance.entrySet()) {
            waysToWin *= waysToWin(race.getKey(), race.getValue());
        }
        return waysToWin;
    }

    @Override
    public Object part2(String input) {
        List<Long> timeAndDistance = parse2(input);
        return waysToWin(timeAndDistance.get(0) ,timeAndDistance.get(1));
    }

    Map<Integer, Integer> parse(String input) {
        Map<Integer, Integer> timeAndDistance = new HashMap<>();
        String[] strings = input.split(REGEX_NEW_LINE);
        String[] time = Arrays.stream(strings[0].split("\\s+")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        String[] distance = Arrays.stream(strings[1].split("\\s+")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        for (int i = 1; i < time.length; i++) {
            timeAndDistance.put(Integer.parseInt(time[i]), Integer.parseInt(distance[i]));
        }
        return timeAndDistance;
    }

    List<Long> parse2(String input) {
        List<Long> timeAndDistance = new ArrayList<>();
        String[] strings = input.split(REGEX_NEW_LINE);
        Long time = extractNumber(strings[0]);
        Long distance = extractNumber(strings[1]);
        timeAndDistance.add(time);
        timeAndDistance.add(distance);
        return timeAndDistance;
    }

    private static long extractNumber(String string) {
        String digits = string.replaceAll("\\D", "");
        return Long.parseLong(digits);
    }

    public int waysToWin(long time, long distance) {
        long timeLeft;
        int ways = 0;
        for (int i = 0; i < time; i++) {
            timeLeft = time - i;
            if (timeLeft * i > distance) ways++;
        }
        return ways;
    }
}
