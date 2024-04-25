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
        return rulesMap.get("in").combinations(new ArrayList<>());
    }

    Map<String, Rule> parseRules(String input) {
        Map<String, Rule> rulesMap = new HashMap<>();
        rulesMap.put("A", new Accepted(4000));
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

        long combinations(List<Rule> rulesMain);
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

        @Override
        public long combinations(List<Rule> rulesMain) {
            long result = 0;
            List<Rule> newList = new ArrayList<>(rulesMain);
            for (Rule r : rules) {
                result += r.combinations(newList);
                if (r instanceof LessThan lessThan) {
                    newList.add(new MoreThan(lessThan.c, lessThan.n - 1, lessThan.s, lessThan.rules));
                } else if (r instanceof MoreThan moreThan) {
                    newList.add(new LessThan(moreThan.c, moreThan.n + 1, moreThan.s, moreThan.rules));
                }
            }
            return result;
        }
    }

    record LessThan(char c, int n, String s, Map<String, Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return p.get(c) < n ? rules.get(s).apply(p) : Optional.empty();
        }

        @Override
        public long combinations(List<Rule> rulesMain) {
            List<Rule> newList = new ArrayList<>(rulesMain);
            newList.add(this);
            return rules.get(s).combinations(newList);
        }

        @Override
        public String toString() {
            return '{' + Character.toString(c) + "<" + n + ":" + s + '}';
        }
    }

    record MoreThan(char c, int n, String s, Map<String, Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return p.get(c) > n ? rules.get(s).apply(p) : Optional.empty();
        }

        @Override
        public long combinations(List<Rule> rulesMain) {
            List<Rule> newList = new ArrayList<>(rulesMain);
            newList.add(this);
            return rules.get(s).combinations(newList);
        }

        @Override
        public String toString() {
            return '{' + Character.toString(c) + ">" + n + ":" + s + '}';
        }
    }

    record GoTo(String s, Map<String, Rule> rules) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return rules.get(s).apply(p);
        }

        @Override
        public long combinations(List<Rule> rulesMain) {
            return rules.get(s).combinations(rulesMain);
        }

        @Override
        public String toString() {
            return '{' + s + '}';
        }
    }

    record Accepted(int max) implements Rule {
        @Override
        public Optional<Boolean> apply(Part p) {
            return Optional.of(true);
        }

        @Override
        public long combinations(List<Rule> rulesMain) {
            int newMax = max + 1;
            long minX = 0, maxX = newMax, minM = 0, maxM = newMax, minA = 0, maxA = newMax, minS = 0, maxS = newMax;
            for (Rule r : rulesMain) {
                if (r instanceof MoreThan moreThan) {
                    switch ((moreThan.c)) {
                        case 'x' -> minX = Math.max(minX, moreThan.n);
                        case 'm' -> minM = Math.max(minM, moreThan.n);
                        case 'a' -> minA = Math.max(minA, moreThan.n);
                        case 's' -> minS = Math.max(minS, moreThan.n);
                        default -> throw new IllegalArgumentException();
                    }
                }
                if (r instanceof LessThan lessThan) {
                    switch (lessThan.c) {
                        case 'x' -> maxX = Math.min(maxX, lessThan.n);
                        case 'm' -> maxM = Math.min(maxM, lessThan.n);
                        case 'a' -> maxA = Math.min(maxA, lessThan.n);
                        case 's' -> maxS = Math.min(maxS, lessThan.n);
                        default -> throw new IllegalArgumentException();
                    }
                }
            }
            return (maxX - minX - 1) * (maxM - minM - 1) * (maxA - minA - 1) * (maxS - minS - 1);
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
        public long combinations(List<Rule> rulesMain) {
            return 0;
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
