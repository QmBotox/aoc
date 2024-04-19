package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day19 implements Puzzle {
    @Override
    public Object part1(String input) {
        String[] inp = input.split(REGEX_EMPTY_LINE);
        Map<String, Rule> rulesMap = parseRules(inp[0]);
        List<Part> parts = parseParts(inp[1]);
        int result = 0;
        for (Part p : parts) {
            Optional<Boolean> ruleResult = rulesMap.get("in").apply(p);
            if (ruleResult.isPresent() && ruleResult.get()) {
                result = result + p.x + p.m + p.a + p.s;
            }
        }
        return result;
    }

    @Override
    public Object part2(String input) {
        String[] inp = input.split(REGEX_EMPTY_LINE);
        Map<String, Rule> rulesMap = parseRules(inp[0]);
        long result = 0;
        Set<Rule> rulesMain = new HashSet<>();
        mainRules("A", rulesMain, rulesMap);
        int minX = 4000;
        int maxX = 0;
        int minM = 4000;
        int maxM = 0;
        int minA = 4000;
        int maxA = 0;
        int minS = 4000;
        int maxS = 0;
        int n;
        for (Rule r : rulesMain) {
            if (r instanceof MoreThan) {
                switch (((MoreThan) r).c) {
                    case 'x' -> {
                        n = ((MoreThan) r).n;
                        if (minX > n) minX = n;
                    }
                    case 'm' -> {
                        n = ((MoreThan) r).n;
                        if (minM > n) minM = n;
                    }
                    case 'a' -> {
                        n = ((MoreThan) r).n;
                        if (minA > n) minA = n;
                    }
                    case 's' -> {
                        n = ((MoreThan) r).n;
                        if (minS > n) minS = n;
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
            if (r instanceof LessThan) {
                switch (((LessThan) r).c) {
                    case 'x' -> {
                        n = ((LessThan) r).n;
                        if (maxX < n) maxX = n;
                    }
                    case 'm' -> {
                        n = ((LessThan) r).n;
                        if (maxM < n) maxM = n;
                    }
                    case 'a' -> {
                        n = ((LessThan) r).n;
                        if (maxA < n) maxA = n;
                    }
                    case 's' -> {
                        n = ((LessThan) r).n;
                        if (maxS < n) maxS = n;
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
        }
        n = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int m = minM; m <= maxM; m++) {
                for (int a = minA; a <= maxA; a++) {
                    for (int s = minS; s <= maxS; s++) {
                        Optional<Boolean> ruleResult = rulesMap.get("in").apply(new Part(x, m, a,s));
                        if (ruleResult.isPresent() && ruleResult.get()) {
                            result = result + x + m + a + s;
                        }
                    }
                }
            }
        }
        return result;
    }

    void mainRules(String s, Set<Rule> rulesMain, Map<String, Rule> rulesMap) {
        for (String ruleName : rulesMap.keySet()) {
            Rule rule = rulesMap.get(ruleName);
            if (rule instanceof RuleList) {
                for (Rule r : ((RuleList) rule).rules) {
                    if (r instanceof MoreThan) {
                        if (((MoreThan) r).s.equals(s)) {
                            rulesMain.add(r);
                            mainRules(ruleName, rulesMain, rulesMap);
                        }
                    } else if (r instanceof LessThan) {
                        if (((LessThan) r).s.equals(s)) {
                            rulesMain.add(r);
                            mainRules(ruleName, rulesMain, rulesMap);
                        }
                    } else if (r instanceof GoTo) {
                        if (((GoTo) r).s.equals(s)) {
                            mainRules(ruleName, rulesMain, rulesMap);
                        }
                    }
                }
            }
        }
    }

    Map<String, Rule> parseRules(String input) {
        Map<String, Rule> rulesMap = new HashMap<>();
        rulesMap.put("A", new Accepted());
        rulesMap.put("R", new Rejected());
        for (String s : input.split(REGEX_NEW_LINE)) {
            List<Rule> rulesList = new ArrayList<>();
            String[] nameAndRules = s.split("\\{");
            String name = nameAndRules[0];
            String[] rules = nameAndRules[1].split(",");
            for (String r : rules) {
                if (r.contains(">")) {
                    String[] thanAndWhere = r.substring(2).split(":");
                    rulesList.add(new MoreThan(r.charAt(0), Integer.parseInt(thanAndWhere[0]),
                            thanAndWhere[1], rulesMap));
                } else if (r.contains("<")) {
                    String[] thanAndWhere = r.substring(2).split(":");
                    rulesList.add(new LessThan(r.charAt(0), Integer.parseInt(thanAndWhere[0]),
                            thanAndWhere[1], rulesMap));
                } else {
                    rulesList.add(new GoTo(r.substring(0, r.length() - 1), rulesMap));
                }
            }
            rulesMap.put(name, new RuleList(rulesList));
        }
        return rulesMap;
    }

    List<Part> parseParts(String input) {
        List<Part> parts = new ArrayList<>();
        for (String string : input.split(REGEX_NEW_LINE)) {
            String[] str = string.split(",");
            int x = Integer.parseInt(str[0].substring(3));
            int m = Integer.parseInt(str[1].substring(2));
            int a = Integer.parseInt(str[2].substring(2));
            int s = Integer.parseInt(str[3].substring(2, str[3].length() - 1));
            parts.add(new Part(x, m, a, s));
        }
        return parts;
    }

    interface Rule {
        Optional<Boolean> apply(Part p);
    }

    record RuleList(List<Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            for (Rule r : rules) {
                Optional<Boolean> result = r.apply(p);
                if (result.isPresent()) return result;
            }
            return Optional.empty();
        }
    }

    record LessThan(char c, int n, String s, Map<String, Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return p.get(c) < n ? rules.get(s).apply(p) : Optional.empty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LessThan lessThan = (LessThan) o;

            if (c != lessThan.c) return false;
            if (n != lessThan.n) return false;
            return Objects.equals(s, lessThan.s);
        }

        @Override
        public int hashCode() {
            int result = c;
            result = 31 * result + n;
            result = 31 * result + (s != null ? s.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return '{' + c + "<" + n + ":" + s + '}';
        }
    }

    record MoreThan(char c, int n, String s, Map<String, Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return p.get(c) > n ? rules.get(s).apply(p) : Optional.empty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MoreThan moreThan = (MoreThan) o;

            if (c != moreThan.c) return false;
            if (n != moreThan.n) return false;
            return Objects.equals(s, moreThan.s);
        }

        @Override
        public int hashCode() {
            int result = c;
            result = 31 * result + n;
            result = 31 * result + (s != null ? s.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return '{' + c + ">" + n + ":" + s + '}';
        }
    }

    record GoTo(String s, Map<String, Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return rules.get(s).apply(p);
        }

        @Override
        public String toString() {
            return '{' + s + '}';
        }
    }

    record Accepted() implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return Optional.of(true);
        }

        @Override
        public String toString() {
            return "{A}";
        }
    }

    record Rejected() implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return Optional.of(false);
        }

        @Override
        public String toString() {
            return "{R}";
        }
    }


    record Part(int x, int m, int a, int s) {
        int get(char c) {
            return switch (c) {
                case 'x' -> x;
                case 'm' -> m;
                case 'a' -> a;
                case 's' -> s;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
