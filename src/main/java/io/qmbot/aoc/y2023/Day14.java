package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

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
        for (long i = 0; i < 1000000000; i++){
            toNorth(dish);
            toWest(dish);
            toSouth(dish);
            toEast(dish);
        }
        toNorth(dish);
        return totalLoad(dish);
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
        for (int y = 0; y < sizeY; y++){
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
            for(int x = 0; x < sizeX; x++){
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
    }
}
