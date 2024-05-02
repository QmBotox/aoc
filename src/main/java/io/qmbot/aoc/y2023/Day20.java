package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day20 implements Puzzle {
    static boolean printTurn = false;
    @Override
    public Object part1(String input) {
        Map<String, Module> map = parse(input);
        Result result = new Result();
        for (int i = 0; i < 1000; i++) {
            result.add(pushButton(map));
        }
        return result.highCount * result.lowCount;
    }

    @Override
    public Object part2(String input) {
        Map<String, Module> map = parse(input);
        int count = 1;
        Result r;
        long i = 1;
        int round = 0;
        Conjunction module = (Conjunction) map.values().stream().filter(m -> m.destinations.contains("rx")).findAny().get();
        int forLow = module.state.size();
        do {
            r = pushButton(map);
            if (printTurn ) {
                System.out.println(count);
                i = i * count;
                round++;
                printTurn = false;

            }
            count++;
        } while (!r.rxIsLow && round != forLow);
        return i;
    }

    static Map<String, Module> parse(String input) {
        Map<String, Module> parse = new HashMap<>();
        for (String s : input.split(REGEX_NEW_LINE)) {
            String[] nameAndDestinations = s.split(" -> ");
            List<String> destinations = Arrays.stream(nameAndDestinations[1].split(", ")).toList();
            char type = nameAndDestinations[0].charAt(0);
            String name;
            switch (type) {
                case '%' -> {
                    name = nameAndDestinations[0].substring(1);
                    parse.put(name, new FlipFlop(destinations, name));
                }
                case '&' -> {
                    name = nameAndDestinations[0].substring(1);
                    parse.put(name, new Conjunction(destinations, name));
                }
                default -> parse.put(nameAndDestinations[0], new Broadcaster(destinations));
            }
        }
        for (String name : parse.keySet()) {
            if (parse.get(name) instanceof Conjunction conjunction) {
                for (String s : parse.keySet()) {
                    if (parse.get(s).destinations.contains(name)) conjunction.state.put(s, true);
                }
            }
        }
        return parse;
    }

    static Result pushButton(Map<String, Module> modules) {
        LinkedList<Signal> signals = new LinkedList<>();
        signals.add(new Signal(true, "button", "broadcaster"));
        Result result = new Result();
        boolean rxIsLow = false;
        while (!signals.isEmpty()) {
            Signal s = signals.removeFirst();
            result.add(s);
            Module m = modules.get(s.destination);
            if (m != null) {
                signals.addAll(m.process(s));
            } else if (s.isLow) {
                rxIsLow = true;
                break;
            }
        }
        result.rxIsLow = rxIsLow;
        return result;
    }

    static class Result {
        long highCount = 0;
        long lowCount = 0;
        boolean rxIsLow = false;

        void add(Signal s) {
            if (s.isLow) highCount++; else lowCount++;
        }

        void add(Result r) {
            highCount += r.highCount;
            lowCount += r.lowCount;
        }

    }

    abstract static class Module {
        List<String> destinations;

        abstract List<Signal> process(Signal signal);

        public Module(List<String> destinations) {
            this.destinations = destinations;
        }
    }

    static final class Broadcaster extends Module {
        Broadcaster(List<String> destinations) {
            super(destinations);
        }

        @Override
        public List<Signal> process(Signal signal) {
            List<Signal> process = new ArrayList<>();
            for (String d : destinations) {
                process.add(new Signal(signal.isLow, signal.destination, d));
            }
            return process;
        }

        @Override
        public String toString() {
            return "Broadcaster[" +
                    "destinations=" + destinations + ']';
        }

    }

    static class FlipFlop extends Module {
        boolean on = false;
        String name;

        @Override
        public String toString() {
            return "FF" + name + "{" +
                    "destinations=" + destinations +
                    ", on=" + on + '}';
        }

        public FlipFlop(List<String> destinations, String name) {
            super(destinations);
            this.name = name;
        }

        @Override
        public List<Signal> process(Signal signal) {
            List<Signal> process = new ArrayList<>();
            if (!signal.isLow) {
                return process;
            }
            for (String d : destinations) {
                process.add(new Signal(on, name, d));
            }
            on = !on;
            return process;
        }
    }

    static class Conjunction extends Module {
        Map<String, Boolean> state = new HashMap<>();
        String name;

        @Override
        public String toString() {
            return "C" + name + "{" +
                    "destinations=" + destinations +
                    ", state=" + state + '}';
        }

        public Conjunction(List<String> destinations, String name) {
            super(destinations);
            this.name = name;
        }

        @Override
        public List<Signal> process(Signal signal) {
            List<Signal> process = new ArrayList<>();
            state.put(signal.source, signal.isLow);
            boolean allHigh = state.values().stream().noneMatch(b -> b);
            for (String s : destinations) {
                process.add(new Signal(allHigh, name, s));
            }
            if (destinations.contains("rx") && state.values().stream().anyMatch(b -> !b)) {
                printTurn = true;
            }
            return process;
        }
    }

    static class Signal {
        boolean isLow;
        String source;
        String destination;

        @Override
        public String toString() {
            return source + " -" + (isLow?"low":"high") + "-> " + destination;
        }

        public Signal(boolean isLow, String source, String destination) {
            this.isLow = isLow;
            this.source = source;
            this.destination = destination;
        }
    }
}
