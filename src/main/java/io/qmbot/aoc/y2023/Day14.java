package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day14 implements Puzzle {
    @Override
    public Object part1(String input) {
        char[][] dish = parse(input);
        toNorth(dish);
        return totalLoad(dish);
    }

    @Override
    public Object part2(String input) {
        char[][] dish = parse(input);
        Map<String, Long> map = new HashMap<>();
        long need = 1000000000;
        long cycle;
        for (long i = 0; i < need; i++){
            toNorth(dish);
            toWest(dish);
            toSouth(dish);
            toEast(dish);
            String s = dishToString(dish);
            if (map.containsKey(s)) {
                cycle = i - map.get(s);
                i = i + cycle * ((need - i) / cycle);
            }
            map.put(s, i);
        }
        return totalLoad(dish);
    }

    String dishToString(char[][] dish) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < dish.length; y++) {
            for (int x = 0; x < dish[0].length; x++) {
                stringBuilder.append(dish[y][x]);
            }
        }
        return String.valueOf(stringBuilder);
    }

    char[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        char[][] dish = new char[strings.length][strings[0].length()];
        for (int y = 0; y<strings.length; y++){
            for(int x = 0; x < strings[0].length(); x++){
                dish[y][x] = strings[y].charAt(x);
            }
        }
        return dish;
    }

    int totalLoad(char[][] dish) {
        int totalLoad =0;
        int sizeY = dish.length;
        int sizeX = dish[0].length;
        for (int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                if (dish[y][x] == 'O') {
                    totalLoad += sizeY - y;
                }
            }
        }
        return totalLoad;
    }
    
    void toWest(char[][] dish) {
        int sizeY = dish.length;
        int sizeX = dish[0].length;
        for (int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                if (dish[y][x] == 'O') {
                    fallX(new Point(y, x), dish, -1);
                }
            }
        }
    }

    void toSouth(char[][] dish) {
        int sizeY = dish.length;
        int sizeX = dish[0].length;
        for (int y = sizeY - 1; y > -1; y--){
            for(int x = 0; x < sizeX; x++){
                if (dish[y][x] == 'O') {
                    fallY(new Point(y, x), dish, 1);
                }
            }
        }
    }

    void toEast(char[][] dish) {
        int sizeY = dish.length;
        int sizeX = dish[0].length;
        for (int y = 0; y < sizeY; y++){
            for(int x = sizeX - 1; x > -1; x--){
                if (dish[y][x] == 'O') {
                    fallX(new Point(y, x), dish, 1);
                }
            }
        }
    }

    void toNorth(char[][] dish) {
        int sizeY = dish.length;
        int sizeX = dish[0].length;
        for (int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                if (dish[y][x] == 'O') {
                    fallY(new Point(y, x), dish, -1);
                }
            }
        }
    }

    void fallY(Point before, char[][] dish, int direction) {
        Point now = new Point(before.y, before.x);
        while (now.y + direction >= 0 && now.y + direction <= dish.length - 1 &&
                (dish[now.y + direction][now.x] != 'O' && dish[now.y + direction][now.x] != '#' )) {
            now.y += direction;
        }
        dish[before.y][before.x] = '.';
        dish[now.y][now.x] = 'O';
    }
    void fallX(Point before, char[][] dish, int direction) {
        Point now = new Point(before.y, before.x);
        while (now.x + direction >= 0 && now.x + direction <= dish.length - 1 &&
                (dish[now.y][now.x + direction] != 'O' && dish[now.y][now.x + direction] != '#' )) {
            now.x += direction;
        }
        dish[before.y][before.x] = '.';
        dish[now.y][now.x] = 'O';
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "{" + y +
                    ", " + x +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (y != point.y) return false;
            return x == point.x;
        }

        @Override
        public int hashCode() {
            int result = y;
            result = 31 * result + x;
            return result;
        }
    }
}
