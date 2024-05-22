package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Day24 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Hail> list = parse(input);
        return null;
    }

    @Override
    public Object part2(String input) {
        return null;
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

    class Hail {
        int px;
        int py;
        int pz;
        int vx;
        int vy;
        int vz;

        public Hail(int px, int py, int pz, int vx, int vy, int vz) {
            this.px = px;
            this.py = py;
            this.pz = pz;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
        }
    }
}
