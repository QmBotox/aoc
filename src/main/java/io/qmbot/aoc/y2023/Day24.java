package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Day24 implements Puzzle {
    MathContext mc = new MathContext(4, RoundingMode.UP);

    @Override
    public Object part1(String input) {
        List<Hail> list = parse(input);
        BigDecimal area1 = BigDecimal.valueOf(7);//BigDecimal.valueOf(200_000_000).multiply(BigDecimal.valueOf(1_000_000));
        BigDecimal area2 = BigDecimal.valueOf(27);//BigDecimal.valueOf(400_000_000).multiply(BigDecimal.valueOf(1_000_000));
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                Hail h1 = list.get(i);
                Hail h2 = list.get(j);
                BigDecimal t2 = t2(h1.px, h2.px(), h1.py, h2.py, h1.vx, h2.vx, h1.vy, h2.vy);
                // double t1 = (h2.py + (h2.vy * t2) - h1.vy) / h1.vy;

                BigDecimal numerator = h2.py.add(h2.vy.multiply(t2, mc)).subtract(h1.vy, mc); // h2.py + (h2.vy * t2) - h1.vy
                BigDecimal t1 = numerator.divide(h1.vy, mc); // (numerator) / h1.vy
                BigDecimal x = h2.px.add(t2.multiply(h2.vx));
                BigDecimal y = h2.py.add(t2.multiply(h2.vy));
                int jo = 0;
                if (area1.compareTo(x) <= 0 && area2.compareTo(x) >= 0
                        && area1.compareTo(y) <= 0 && area2.compareTo(y) >= 0
                        && t2.compareTo(BigDecimal.ONE) >= 0 && t1.compareTo(BigDecimal.ONE) >= 0) {
                    //area1 <= x && area2 >= x && area1 <= y && area2 >= y && t2 >= 1 && t1 >= 1) {
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

    BigDecimal t2(BigDecimal x1, BigDecimal x2, BigDecimal y1, BigDecimal y2, BigDecimal vx1, BigDecimal vx2, BigDecimal vy1, BigDecimal vy2) {
        //(y1 + vy1 * x2 / vx1 - vy1 * x1 / vx1 - y2) / (vy2 - vy1 * vx2 / vx1);

        BigDecimal term1 = vy1.multiply(x2).divide(vx1, mc); // vy1 * x2 / vx1
        BigDecimal term2 = vy1.multiply(x1).divide(vx1, mc); // vy1 * x1 / vx1
        BigDecimal numerator = y1.add(term1).subtract(term2).subtract(y2); // y1 + term1 - term2 - y2
        BigDecimal denominator = vy2.subtract(vy1.multiply(vx2).divide(vx1, mc)); // vy2 - vy1 * vx2 / vx1
        if (denominator.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.valueOf(-1);
        BigDecimal t2 = numerator.divide(denominator, mc);
        return t2;
    }

    List<Hail> parse(String input) {
        List<Hail> list = new ArrayList<>();
        for (String s : input.split(REGEX_NEW_LINE)) {
            String[] pAndV = s.split(" @ ");
            String[] p = pAndV[0].split(", ");
            String[] v = pAndV[1].split(", ");

            list.add(new Hail(bigDecimal(p[0].trim()), bigDecimal(p[1].trim()), bigDecimal(p[2].trim()),
                    bigDecimal(v[0].trim()), bigDecimal(v[1].trim()), bigDecimal(v[2].trim())));
        }
        return list;
    }

    BigDecimal bigDecimal(String s) {
        return BigDecimal.valueOf(Long.parseLong(s));
    }

    record Hail(BigDecimal px, BigDecimal py, BigDecimal pz, BigDecimal vx, BigDecimal vy, BigDecimal vz) {
    }
}
