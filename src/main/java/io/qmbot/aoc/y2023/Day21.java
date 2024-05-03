package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;

import java.util.*;

public class Day21 implements Puzzle {
    @Override
    public Object part1(String input) {
        char[][] map = parse(input);
        Map<Point, Integer> bfs = bfs(start(map), map);
        return bfs.values().stream().filter(i -> i % 2 == 0 && i < 65).count();
    }

    @Override
    public Object part2(String input) {
        char[][] map = parse(input);
        Map<Point, Integer> bfs = bfs(start(map), map);
        long even_corners = bfs.values().stream().filter(i -> i % 2 == 0 && i > 65).count();
        long odd_corners = bfs.values().stream().filter(i -> i % 2 == 1 && i > 65).count();
        long even_full = bfs.values().stream().filter(i -> i % 2 == 0).count();
        long odd_full = bfs.values().stream().filter(i -> i % 2 == 1).count();
        long n = ((26501365 - (map.length / 2)) / map.length);
        return ((n+1)*(n+1)) * odd_full + (n*n) * even_full - (n+1) * odd_corners + n * even_corners;
    }

    static char[][] parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(String::toCharArray).toArray(char[][]::new);
    }

    static public Map<Point, Integer> bfs(Point start, char[][] map) {
        Queue<Point> queue = new ArrayDeque<>();
        Map<Point, Integer> result = new HashMap<>();
        Point current = start;
        queue.add(current);
        int step = 0;
        result.put(current, step);
        while (!queue.isEmpty()) {
            current = queue.remove();
            step = result.get(current) + 1;
            for (Point p : getNeighbors(map, current)) {
                if (!result.containsKey(p)) {
                    queue.add(p);
                    result.put(p, step);
                }
            }
        }
        return result;
    }

    static Point start(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'S') return new Point(y, x);
            }
        }
        throw new IllegalArgumentException();
    }

    static List<Point> getNeighbors(char[][] map, Point p) {
        List<Point> neighbors = new ArrayList<>();
        int numRows = map.length;
        int numCols = map[0].length;
        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++) {
            int nx = p.x + dx[i];
            int ny = p.y + dy[i];
            if (nx >= 0 && nx < numCols && ny >= 0 && ny < numRows) {
                if (map[ny][nx] != '#') neighbors.add(new Point(ny, nx));
            }
        }
        return neighbors;
    }
}
