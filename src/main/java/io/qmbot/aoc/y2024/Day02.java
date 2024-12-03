package io.qmbot.aoc.y2024;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 implements Puzzle {
    @Override
    public Object part1(String input) {
        return parse(input).stream().filter(report -> correct(report, false)).count();
    }

    @Override
    public Object part2(String input) {
        List<List<Integer>> reports = parse(input);
        long correct = 0;
        for (List<Integer> report : reports) {
            if (correct(report, true)) {
                correct++;
            }
        }
        return correct;
    }

    List<List<Integer>> parse(String input) {
        List<List<Integer>> reports = new ArrayList<>();
        for (String s : input.split(REGEX_NEW_LINE)) {
            reports.add(Arrays.stream(s.split(" ")).map(Integer::parseInt).toList());
        }
        return reports;
    }

    Boolean correct(List<Integer> report, Boolean problemDampener) {
        int increasing = 0;
        int decreasing = 0;
        int reportLength = report.size();
        for (int i = 1; i <= reportLength - 1; i++) {
            int differ = report.get(i) - report.get(i - 1);
            int differAbs = Math.abs(differ);
            if (differ < 0) decreasing++;
            if (differ > 0) increasing++;
            if (problemDampener) {
                if ((increasing == 1 && increasing <= decreasing)
                        || (decreasing == 1 && decreasing <= increasing)) {
                    return correct(removeElement(i, report), false);
                }
                if (differAbs < 1 || differAbs > 3) {
                    return correct(removeElement(i, report), false);
                }
            }
            if (differAbs < 1 || differAbs > 3) {
                return false;
            }
        }
        increasing++;
        decreasing++;
        return (increasing == reportLength || decreasing == reportLength);
    }

    private List<Integer> removeElement(int i, List<Integer> report) {
        List<Integer> newReport = new ArrayList<>(report);
        newReport.remove(i);
        return newReport;
    }
}
