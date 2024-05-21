package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;

import java.util.*;

public class Day23 implements Puzzle {
    @Override
    public Object part1(String input) {
        char[][] snowIsland = parse(input);
        Point start = new Point(0, 1);
        Point end = new Point(snowIsland.length - 1, snowIsland[0].length - 2);
        return dfs(start, end, snowIsland, new boolean[snowIsland.length][snowIsland[0].length]) - 1;
    }

    @Override
    public Object part2(String input) {
        char[][] snowIsland = parse(input);
        Point start = new Point(0, 1);
        Point end = new Point(snowIsland.length - 1, snowIsland[0].length - 2);
        Map<Point, List<PointAndDestination>> map = map(snowIsland, start, end);
        return dfs(start, end, map, new HashSet<>());
    }

    static char[][] parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(String::toCharArray).toArray(char[][]::new);
    }

    static Map<Point, List<PointAndDestination>> map(char[][] snowIsland, Point start, Point end) {
        Map<Point, List<PointAndDestination>> map = new HashMap<>();
        List<Point> list = new ArrayList<>();
        list.add(start);
        list.add(end);
        for (int y = 0; y < snowIsland.length; y++) {
            for (int x = 0; x < snowIsland[0].length; x++) {
                Point p = new Point(y, x);
                if (snowIsland[y][x] == '.' && neighbors(p, snowIsland).size() > 2) {
                    list.add(p);
                }
            }
        }
        for (Point point : list) {
            List<PointAndDestination> padList = new ArrayList<>();
            for (Point p : list) {
                List<Point> path = findShortestPath(point, p, snowIsland);
                int contains = 0;
                for (Point fromPath : path) {
                    if (list.contains(fromPath)) contains++;
                }
                if (contains == 2) {
                    padList.add(new PointAndDestination(p, path.size() - 1));
                }
            }
            map.put(point, padList);
        }
        return map;
    }

    public static List<Point> findShortestPath(Point start, Point end, char[][] snowIsland) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        Map<Point, Point> predecessors = new HashMap<>();
        queue.add(start);
        visited.add(start);
        predecessors.put(start, null);
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(end)) {
                return reconstructPath(predecessors, end);
            }
            for (Point neighbor : neighbors2(current, snowIsland)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    predecessors.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Point> reconstructPath(Map<Point, Point> predecessors, Point end) {
        List<Point> path = new LinkedList<>();
        for (Point at = end; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private static int dfs(Point current, Point end, char[][] snowIsland, boolean[][] visited) {
        if (current.x == end.x && current.y == end.y) {
            return 1;
        }
        visited[current.y][current.x] = true;
        int maxPath = 0;
        for (Point neighbor : neighbors(current, snowIsland)) {
            if (!visited[neighbor.y][neighbor.x]) {
                int pathLength = dfs(neighbor, end, snowIsland, visited);
                maxPath = Math.max(maxPath, pathLength);
            }
        }
        visited[current.y][current.x] = false;
        return maxPath + 1;
    }

    private static int dfs(Point current, Point end, Map<Point, List<PointAndDestination>> map, Set<Point> visited) {
        if (current.equals(end)) {
            return 0;
        }
        visited.add(current);
        int maxPath = 0;
        for (PointAndDestination pad : map.get(current)) {
            Point neighbor = pad.point;
            if (!visited.contains(neighbor)) {
                int pathLength = dfs(neighbor, end, map, visited);
                maxPath = Math.max(maxPath, pathLength + pad.distance);
            }
        }
        visited.remove(current);
        return maxPath;
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
        int y = p.y;
        int x = p.x;
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
        return neighbors;
    }

    static class PointAndDestination {
        Point point;
        int distance;

        @Override
        public String toString() {
            return "PointAndDestination{" +
                    "point=" + point +
                    ", destination=" + distance +
                    '}';
        }

        public PointAndDestination(Point point, int distance) {
            this.point = point;
            this.distance = distance;
        }
    }
}
