package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day20 implements Puzzle {
    @Override
    public Object part1(String input) {
        LinkedList<Signal> signals = new LinkedList<>();
        Map<String, Module> map = parse(input);
        pushButton(map);
        pushButton(map);
        return null;
    }

    @Override
    public Object part2(String input) {
        return null;
    }

    Map<String, Module> parse(String input) {
        Map<String, Module> parse = new HashMap<>();
        for (String s : input.split(REGEX_NEW_LINE)) {
            String[] nameAndDestinations = s.split(" -> ");
            List<String> destinations;
            if (nameAndDestinations.length == 2) {
                destinations = Arrays.stream(nameAndDestinations[1].split(", ")).toList();
            } else {
                destinations = new ArrayList<>();
            }
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

    void pushButton(Map<String, Module> modules) {
        LinkedList<Signal> signals = new LinkedList<>();
        signals.add(new Signal(true, "", "broadcaster"));
        while (!signals.isEmpty()) {
            Signal s = signals.removeFirst();
            System.out.println(s);
            signals.addAll(modules.get(s.destination).process(s));
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
