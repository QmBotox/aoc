package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;

import java.util.*;

public class Day23 implements Puzzle {
    static int maxDeep = 0;
    @Override
    public Object part1(String input) {
        char[][] snowIsland = parse(input);
        Point start = new Point(0, 1);
        Point end = new Point(snowIsland.length - 1, snowIsland[0].length - 2);
        return dfs(start, end, snowIsland) - 1;
    }

    @Override
    public Object part2(String input) {
        char[][] snowIsland = parse(input);
        Point start = new Point(0, 1);
        Point end = new Point(snowIsland.length - 1, snowIsland[0].length - 2);
        dfs2(start, end, snowIsland);
        return maxDeep;
    }

    static char[][] parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(String::toCharArray).toArray(char[][]::new);
    }

    public static int dfs(Point start, Point end, char[][] snowIsland) {
        boolean[][] visited = new boolean[snowIsland.length][snowIsland[0].length];
        return dfsHelper(start, end, snowIsland, visited);
    }

    private static int dfsHelper(Point current, Point end, char[][] snowIsland, boolean[][] visited) {
        if (current.x == end.x && current.y == end.y) {
            return 1;
        }
        visited[current.y][current.x] = true;
        int maxPath = 0;
        for (Point neighbor : neighbors(current, snowIsland)) {
            if (!visited[neighbor.y][neighbor.x]) {
                int pathLength = dfsHelper(neighbor, end, snowIsland, visited);
                maxPath = Math.max(maxPath, pathLength);
            }
        }
        visited[current.y][current.x] = false;
        return maxPath + 1;
    }
    public static int dfs2(Point start, Point end, char[][] snowIsland) {
        boolean[][] visited = new boolean[snowIsland.length][snowIsland[0].length];
        return dfsHelper2(start, end, snowIsland, visited, 0);
    }

    private static int dfsHelper2(Point current, Point end, char[][] snowIsland, boolean[][] visited, int i) {

        if (current.x == end.x && current.y == end.y) {
            maxDeep = Math.max(i, maxDeep);
            return 1;
        }
        visited[current.y][current.x] = true;
        int maxPath = 0;
        for (Point neighbor : neighbors2(current, snowIsland)) {
            if (!visited[neighbor.y][neighbor.x]) {
                int pathLength = dfsHelper2(neighbor, end, snowIsland, visited, i + 1);
                maxPath = Math.max(maxPath, pathLength);
            }
        }
        visited[current.y][current.x] = false;
        return maxPath + 1;
    }

    static List<Point> neighbors(Point p, char[][] snowIsland) {
        List<Point> neighbors = new ArrayList<>();
        int y = p.y;
        int x = p.x;
        switch (snowIsland[y][x]) {
            case '.' -> {
                int sizeY = snowIsland.length;
                int sizeX = snowIsland[0].length;
                int[] dx = {-1, 0, 0, 1};
                int[] dy = {0, -1, 1, 0};
                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    if (nx >= 0 && nx < sizeX && ny >= 0 && ny < sizeY && snowIsland[ny][nx] != '#') {
                        neighbors.add(new Point(ny, nx));
                    }
                }
            }
            case '>' -> neighbors.add(new Point(y, x + 1));
            case 'v' -> neighbors.add(new Point(y + 1, x));
            case '<' -> neighbors.add(new Point(y, x - 1));
            case '^' -> neighbors.add(new Point(y - 1, x));
        }
        return neighbors;
    }

    static List<Point> neighbors2(Point p, char[][] snowIsland) {
        List<Point> neighbors = new ArrayList<>();
        int sizeY = snowIsland.length;
        int sizeX = snowIsland[0].length;
        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++) {
            int nx = p.x + dx[i];
            int ny = p.y + dy[i];
            if (nx >= 0 && nx < sizeX && ny >= 0 && ny < sizeY && snowIsland[ny][nx] != '#') {
                neighbors.add(new Point(ny, nx));
            }
        }
        return neighbors;
    }
}
