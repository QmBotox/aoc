package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day24 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Hail> list = parse(input);
        double area1 = 7;
        double area2 = 27;
//        double x = aDouble(19, 18, 13, 19, -2, -1, 1, -1);
//        System.out.println(x);
//        x = 18 + x *(-1);
//        System.out.println(x);
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                Hail h1 = list.get(i);
                Hail h2 = list.get(j);
                double t2 = t2(h1.px, h2.px(), h1.py, h2.py, h1.vx, h2.vx, h1.vy, h2.vy);
//                double t1 = (h2.py + (h2.vy * t2) - h1.vy) / h1.vy;
                double x = h2.px + t2 * h2.vx;
                double y = h2.py + t2 * h2.vy;

                if (area1 < x && area2 > x && area1 < y && area2 > y && t2 > 0) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Object part2(String input) {
        return null;
    }

    double t2(double x1, double x2, double y1, double y2, double vx1, double vx2, double vy1, double vy2) {
        double t2 = (y1 + vy1 * x2 / vx1 - vy1 * x1 / vx1  - y2) / (vy2 - vy1 * vx2 / vx1);
        return t2;
    }

    List<Hail> parse(String input) {
        List<Hail> list = new ArrayList<>();
        for (String s : input.split(REGEX_NEW_LINE)) {
            String[] pAndV = s.split(" @ ");
            String[] p = pAndV[0].split(", ");
            String[] v = pAndV[1].split(", ");
            list.add(new Hail(Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()),
                    Integer.parseInt(v[0].trim()), Integer.parseInt(v[1].trim()), Integer.parseInt(v[2].trim())));
        }
        return list;
    }

    record Hail(int px, int py, int pz, int vx, int vy, int vz) {
    }
}
