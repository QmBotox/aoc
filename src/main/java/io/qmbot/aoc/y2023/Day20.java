package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day20 implements Puzzle {
    @Override
    public Object part1(String input) {
        LinkedList<Signal> signals = new LinkedList<>();
        return null;
    }

    @Override
    public Object part2(String input) {
        return null;
    }

    interface Module {
        List<Signal> process(Signal signal);
    }

    record Broadcaster(List<String> destinations) implements Module {
        @Override
        public List<Signal> process(Signal signal) {
            return null;
        }
    }

    static class FlipFlop implements Module {
        List<String> destinations;
        boolean on = false;
        String name;

        public FlipFlop(List<String> destinations, String name) {
            this.destinations = destinations;
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

    static class Conjunction implements Module {
        List<String> destinations;
        Map<String, Boolean> state = new HashMap<>();
        String name;

        public Conjunction(List<String> destinations, String name) {
            this.destinations = destinations;
            this.name = name;
        }

        void setSources(List<String> sources) {
            for (String s : sources) {
                state.put(s, true);
            }
        }

        @Override
        public List<Signal> process(Signal signal) {
            List<Signal> process = new ArrayList<>();
            boolean allHigh = true;
            for (boolean b : state.values()) {
                if (!b) {
                    allHigh = false;
                    break;
                }
            }
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

        public Signal(boolean isLow, String source, String destination) {
            this.isLow = isLow;
            this.source = source;
            this.destination = destination;
        }
    }
}
