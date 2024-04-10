package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;
import io.qmbot.aoc.y2023.Day16.Direction;

import java.util.*;

public class Day17 implements Puzzle {
    @Override
    public Object part1(String input) {
        int[][] map = parse(input);
        int sizeY = map.length;
        int sizeX = map[0].length;

        Point start = new Point(0, 0);
        Point goal = new Point(sizeY - 1, sizeX - 1);

        return dijkstra(map, start, goal);
    }

    @Override
    public Object part2(String input) {
        return null;
    }

    static int[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        int sizeX = strings[0].length();
        int[][] square = new int[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                square[y][x] = Integer.parseInt(strings[y].substring(x, x+1));
            }
        }
        return square;
    }

    static List<Node> neighbors(Node node, int[][] square) {
        int sizeY = square.length;
        int sizeX = square[0].length;
        List<Node> neighbors = new ArrayList<>();
        Point p = node.point;
        Direction d = node.d;
        int blocks = node.steps;
        int distance = node.distance;
        switch (d) {
            case RIGHT, LEFT -> {
                for (Direction direction : Arrays.asList(Direction.UP, Direction.DOWN)) {
                    int newX = p.x + direction.deltaX;
                    int newY = p.y + direction.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Node(new Point(newY, newX), 0, direction, distance + square[newY][newX]));
                    }
                }
                if (blocks < 3) {
                    int newX = p.x + d.deltaX;
                    int newY = p.y + d.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Node(new Point(newY, newX), blocks + 1, d, distance + square[newY][newX]));
                    }
                }
            }
            case DOWN, UP -> {
                for (Direction direction : Arrays.asList(Direction.LEFT, Direction.RIGHT)) {
                    int newX = p.x + direction.deltaX;
                    int newY = p.y + direction.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Node(new Point(newY, newX), 0, direction, distance + square[newY][newX]));
                    }
                }
                if (blocks < 3) {
                    int newX = p.x + d.deltaX;
                    int newY = p.y + d.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Node(new Point(newY, newX), blocks + 1, d, distance + square[newY][newX]));
                    }
                }
            }
        }
        return neighbors;
    }

    public static int dijkstra(int[][] map, Point start, Point end) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Node startNode = new Node(start, 0, Direction.RIGHT, map[start.y][start.x]),
                endNode = new Node(end, 0, Direction.RIGHT, Integer.MIN_VALUE);
        pq.offer(startNode);
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.equals(endNode)) {
                return current.distance;
            }
            for (Node neighbor : neighbors(current, map)) {
                Optional<Node> n = pq.stream().filter(node -> node.equals(neighbor)).findAny();
                if (n.isEmpty() || n.get().distance > neighbor.distance) {
                    pq.offer(neighbor);
                }
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return point.equals(node.point);
        }

        @Override
        public int hashCode() {
            return point.hashCode();
        }

        public Node(Point point, int steps, Direction d, int distance) {
            this.point = point;
            this.steps = steps;
            this.d = d;
            this.distance = distance;
        }
    }
}
