package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day05 implements Puzzle {
    @Override
    public Long part1(String input) {
        Card card = parse(input);
        List<Long> result = new ArrayList<>();
        for (long category : card.seeds) {
            category = processMap(card.seedToSoil, category);
            category = processMap(card.soilToFertilizer, category);
            category = processMap(card.fertilizerToWater, category);
            category = processMap(card.waterToLight, category);
            category = processMap(card.lightToTemperature, category);
            category = processMap(card.temperatureToHumidity, category);
            category = processMap(card.humidityToLocation, category);
            result.add(category);
        }
        return result.stream().min(Long::compareTo).orElseThrow();
    }

    @Override
    public Object part2(String input) {
        Card card = parse(input);
        List<SeedMapping> seedMappings = seedMappings(card.seeds);
        long location = 0L;
        long category;
        while (true) {
            category = location;
            category = processMapReverse(card.humidityToLocation, category);
            category = processMapReverse(card.temperatureToHumidity, category);
            category = processMapReverse(card.lightToTemperature, category);
            category = processMapReverse(card.waterToLight, category);
            category = processMapReverse(card.fertilizerToWater, category);
            category = processMapReverse(card.soilToFertilizer, category);
            long seed = processMapReverse(card.seedToSoil, category);
            if (haveSeed(seedMappings, seed)) {
                return location;
            } else location++;
        }
    }

    List<SeedMapping> seedMappings(List<Long> seeds) {
        List<SeedMapping> seedMappings = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i = i + 2) {
            seedMappings.add(new SeedMapping(seeds.get(i), seeds.get(i + 1)));
        }
        return seedMappings;
    }

    private long processMap(List<Mapping> map, long category) {
        Set<Long> numbers = new HashSet<>();
        numbers.add(category);
        for (Mapping list : map) {
            long value = value(list, category);
            if (numbers.add(value)) {
                return value;
            }
        }
        return category;
    }
    private long processMapReverse(List<Mapping> map, long category) {
        Set<Long> numbers = new HashSet<>();
        numbers.add(category);
        for (Mapping list : map) {
            long value = valueReverse(list, category);
            if (numbers.add(value)) {
                return value;
            }
        }
        return category;
    }
    boolean haveSeed(List <SeedMapping> seedMappings, long seed) {
        for (SeedMapping seedMapping : seedMappings) {
            long source = seedMapping.source;
            long length = seedMapping.length;
            if (source <= seed && seed <= source + length - 1) return true;
        }
        return false;
    }

    private long value(Mapping list, long category) {
        long source = list.source;
        long length = list.length;
        if (source <= category && category <= source + length -1) {
            return list.destination + (category - source);
        }
        return category;
    }
    private long valueReverse(Mapping list, long category) {
        long destination = list.destination;
        long length = list.length;
        if (destination <= category && category <= destination + length -1) {
            return list.source + (category - destination);
        }
        return category;
    }

    private Card parse(String input) {
        List<Mapping>[] maps = new List[7];
        String[] strings = input.split(REGEX_NEW_LINE);
        String[] seedsStrings = strings[0].split(" ");
        int mapIndex = 0;
        for (int i = 0; i < strings.length; i++) {
            switch (strings[i]) {
                case "seed-to-soil map:", "soil-to-fertilizer map:", "fertilizer-to-water map:", "water-to-light map:",
                        "light-to-temperature map:", "temperature-to-humidity map:", "humidity-to-location map:" ->
                        maps[mapIndex++] = list(strings, i);
            }
        }
        return new Card(Arrays.stream(seedsStrings, 1, seedsStrings.length).map(Long::valueOf).toList(),
                maps[0], maps[1], maps[2], maps[3], maps[4], maps[5], maps[6]);
    }

    List<Mapping> list(String[] strings, int i) {
        i++;
        List<Mapping> list = new ArrayList<>();
        String[] values;
        while (i < strings.length && !strings[i].equals("")) {
            values = strings[i].split(" ");
            list.add(somethingToSomething(values[0], values[1], values[2]));
            i++;
        }
        return list;
    }

    static class Card {
        List<Long> seeds;
        List<Mapping> seedToSoil;
        List<Mapping> soilToFertilizer;
        List<Mapping> fertilizerToWater;
        List<Mapping> waterToLight;
        List<Mapping> lightToTemperature;
        List<Mapping> temperatureToHumidity;
        List<Mapping> humidityToLocation;

        public Card(List<Long> seeds, List<Mapping> seedToSoil, List<Mapping> soilToFertilizer,
                    List<Mapping> fertilizerToWater, List<Mapping> waterToLight, List<Mapping> lightToTemperature,
                    List<Mapping> temperatureToHumidity, List<Mapping> humidityToLocation) {
            this.seeds = seeds;
            this.seedToSoil = seedToSoil;
            this.soilToFertilizer = soilToFertilizer;
            this.fertilizerToWater = fertilizerToWater;
            this.waterToLight = waterToLight;
            this.lightToTemperature = lightToTemperature;
            this.temperatureToHumidity = temperatureToHumidity;
            this.humidityToLocation = humidityToLocation;
        }
    }

    public Mapping somethingToSomething(String destination, String source, String length) {
        return new Mapping(Long.parseLong(destination), Long.parseLong(source), Long.parseLong(length));
    }
    record SeedMapping(long source, long length) {}
    record Mapping(long destination, long source, long length){}
}
