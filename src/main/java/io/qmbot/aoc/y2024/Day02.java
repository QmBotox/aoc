package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day02 implements Puzzle {
    @Override
    public Object part1(String input) {
        return parse(input).stream().filter(this::checkReport).count();
    }

    @Override
    public Object part2(String input) {
        return parse(input).stream().filter(this::isSafe).count();
    }

    List<List<Integer>> parse(String input) {
        List<List<Integer>> reports = new ArrayList<>();
        for (String s : input.split(REGEX_NEW_LINE)) {
            reports.add(Arrays.stream(s.split(" ")).map(Integer::parseInt).toList());
        }
        return reports;
    }

    boolean isSafe(List<Integer> report) {
        if (checkReport(report)) return true;
        return IntStream.range(0, report.size()).mapToObj(i -> removeElement(i, report)).anyMatch(this::checkReport);
    }

    boolean checkReport(List<Integer> report) {
        boolean increasing = true;
        boolean decreasing = true;
        for (int i = 1; i < report.size(); i++) {
            int diff = report.get(i) - report.get(i - 1);
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) return false;
            if (diff > 0) decreasing = false;
            if (diff < 0) increasing = false;
        }
        return increasing || decreasing;
    }

    private List<Integer> removeElement(int i, List<Integer> report) {
        List<Integer> newReport = new ArrayList<>(report);
        newReport.remove(i);
        return newReport;
    }
}
