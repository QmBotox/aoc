package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;
import io.qmbot.aoc.y2023.Day16.Direction;

import java.util.*;

public class Day17 implements Puzzle {
    @Override
    public Object part1(String input) {
        int[][] map = parse(input);
        return dijkstra(map, new Point(0, 0), new Point(map.length - 1, map[0].length - 1), 1, 3);
    }

    @Override
    public Object part2(String input) {
        int[][] map = parse(input);
        return dijkstra(map, new Point(0, 0), new Point(map.length - 1, map[0].length - 1), 4, 10);
    }

    static int[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        int sizeX = strings[0].length();
        int[][] square = new int[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                square[y][x] = Integer.parseInt(strings[y].substring(x, x + 1));
            }
        }
        return square;
    }

    static List<Node> neighbors(Node node, int[][] square, int min, int max) {
        int sizeY = square.length;
        int sizeX = square[0].length;
        List<Node> neighbors = new ArrayList<>();
        Point p = node.point;
        Direction dir = node.d;
        int steps = node.steps;
        int distance = node.distance;
        switch (dir) {
            case RIGHT, LEFT -> {
                if (steps >= min) {
                    for (Direction direction : Arrays.asList(Direction.UP, Direction.DOWN)) {
                        int newX = p.x + direction.deltaX;
                        int newY = p.y + direction.deltaY;
                        if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                            neighbors.add(new Node(new Point(newY, newX), 1, direction,
                                    distance + square[newY][newX]));
                        }
                    }
                }
                if (steps < max) {
                    int newX = p.x + dir.deltaX;
                    int newY = p.y + dir.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Node(new Point(newY, newX), steps + 1, dir,
                                distance + square[newY][newX]));
                    }
                }
            }
            case DOWN, UP -> {
                if (steps >= min) {
                    for (Direction direction : Arrays.asList(Direction.LEFT, Direction.RIGHT)) {
                        int newX = p.x + direction.deltaX;
                        int newY = p.y + direction.deltaY;
                        if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                            neighbors.add(new Node(new Point(newY, newX), 1, direction,
                                    distance + square[newY][newX]));
                        }
                    }
                }
                if (steps < max) {
                    int newX = p.x + dir.deltaX;
                    int newY = p.y + dir.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Node(new Point(newY, newX), steps + 1, dir,
                                distance + square[newY][newX]));
                    }
                }
            }
        }
        return neighbors;
    }

    public static int dijkstra(int[][] map, Point start, Point end, int min, int max) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Set<Node> visited = new HashSet<>();
        Node startNode = new Node(start, 0, Direction.RIGHT, 0);
        pq.offer(startNode);
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (visited.contains(current)) continue;
            visited.add(current);
            if (current.point.equals(end)) {
                return current.distance;
            }
            for (Node neighbor : neighbors(current, map, min, max)) {
                pq.offer(neighbor);
            }
        }
        return -1;
    }

    static class Node {
        Point point;
        int steps;
        Direction d;
        int distance;

        @Override
        public String toString() {
            return "{" + point + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            if (steps != node.steps) return false;
            if (!Objects.equals(point, node.point)) return false;
            return d == node.d;
        }

        @Override
        public int hashCode() {
            int result = point != null ? point.hashCode() : 0;
            result = 31 * result + steps;
            result = 31 * result + (d != null ? d.hashCode() : 0);
            return result;
        }

        public Node(Point point, int steps, Direction d, int distance) {
            this.point = point;
            this.steps = steps;
            this.d = d;
            this.distance = distance;
        }
    }
}
