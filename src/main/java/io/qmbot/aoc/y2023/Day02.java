package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Day02 implements Puzzle {
    @Override
    public Object part1(String input) {
        Map<String, Integer> possible = bricks(12, 13, 14);
        List<Game> games = parse(input);
        for (Game game : games) {
            for (Map<String, Integer> set : game.sets) {
                if (game.number == 0) break;
                for (String color : set.keySet()) {
                    if (set.get(color) > possible.get(color)) {
                        game.number = 0;
                        break;
                    }
                }
            }
        }
        return games.stream().mapToInt(game -> game.number).sum();
    }

    @Override
    public Object part2(String input) {
        Map<String, Integer> possible = bricks(0, 0, 0);
        List<Game> games = parse(input);
        for (Game game : games) {
            possible = bricks(0, 0, 0);
            for (Map<String, Integer> set : game.sets) {
                for (String color : set.keySet()) {
                    int now = set.get(color);
                    int pos = possible.get(color);
                    if (now > pos) possible.put(color, now);
                }
            }
            game.power *= possible.values().stream().reduce(1, (product, value) -> product * value);
        }
        return games.stream().mapToInt(game -> game.power).sum();
    }

    private List<Game> parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(this::parseString).toList();
    }

    public Game parseString(String string) {
        String[] parse = string.split(":");
        return new Game(Integer.parseInt(parse[0].substring(5)), sets(parse[1].split(";")));
    }

    static class Game {
        int number;
        List<Map<String, Integer>> sets;

        int power = 1;

        private Game(int number, List<Map<String, Integer>> sets) {
            this.number = number;
            this.sets = sets;
        }
    }

    public List<Map<String, Integer>> sets(String[] string) {
        return Arrays.stream(string).map(String::trim).map(this::bricks).toList();
    }

    Map<String, Integer> bricks(String string) {
        return Arrays.stream(string.split(",")).map(brick -> brick.trim().split(" "))
                .collect(Collectors.toMap(b -> b[1], b -> Integer.parseInt(b[0])));
    }

    static Map<String, Integer> bricks(int red, int green, int blue) {
        Map<String, Integer> bricks = new HashMap<>();
        bricks.put("red", red);
        bricks.put("green", green);
        bricks.put("blue", blue);
        return bricks;
    }
}
