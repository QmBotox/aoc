package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day21 implements Puzzle {
    @Override
    public Object part1(String input) {
        return result(parse(input), 200);
    }

    @Override
    public Object part2(String input) {
        return null;
    }

    char[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int countStrings = strings.length;
        int stringLength = strings[0].length();
        char[][] parse = new char[countStrings][stringLength];
        int points = 0;
        for (int y = 0; y < countStrings; y++) {
            for(int x = 0; x < stringLength; x++) {
                parse[y][x] = strings[y].charAt(x);
                if (parse[y][x] != '#' ) points++;
            }
        }
        return parse;
    }

    Point start(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'S') return new Point(y, x);
            }
        }
        throw new IllegalArgumentException();
    }

    long result(char[][] map, int steps) {
        Set<Point> points = Set.of(start(map));
        for (int i = 0; i < steps; i++) {
            Set<Point> newPoints = new HashSet<>();
            for (Point p : points){
                newPoints.addAll(neighbors(map, p));
            }
            points = newPoints;
        }
        return points.size();
    }

    List<Point> neighbors(char[][] map, Point p) {
        List<Point> neighbors = new ArrayList<>();
        int numRows = map.length;
        int numCols = map[0].length;
        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++) {
            int nx = p.x + dx[i];
            int ny = p.y + dy[i];
            if (nx >= 0 && nx < numCols && ny >= 0 && ny < numRows) {
                if (map[ny][nx] != '#') {
                    neighbors.add(new Point(ny, nx));
                }
            }
        }
        return neighbors;
    }
}
