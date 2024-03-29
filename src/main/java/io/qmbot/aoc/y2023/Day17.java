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

        List<Point> findPath = findPath(start, goal, map);
        int r = 0;
        for (Point p : findPath) {
            r += map[p.y][p.x];
        }

        return null;
    }

    @Override
    public Object part2(String input) {
        return null;
    }


    public static List<Point> findPath(Point start, Point goal, int[][] map) {
        Direction d = Direction.RIGHT;
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        Map<Point, Integer> gValues = new HashMap<>();
        openSet.add(new Node(start, 0, heuristicCostEstimate(start, goal), null));
        gValues.put(start, 0);
        int blocks = 0;
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.point.equals(goal)) {
                return reconstructPath(current);
            }
            List<Point> neighbors = neighbors(current.point, d, map, blocks);
            for (Point neighbor : neighbors) {
                int tentativeG = gValues.get(current.point) + map[neighbor.y][neighbor.x];
                Node neighborNode = new Node(neighbor, tentativeG, heuristicCostEstimate(neighbor, goal), current);
                if (!openSet.contains(neighborNode) || tentativeG < gValues.get(neighbor)) {
                    gValues.put(neighbor, tentativeG);
                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Point> reconstructPath(Node current) {
        List<Point> path = new ArrayList<>();
        while (current != null) {
            path.add(current.point);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static int heuristicCostEstimate(Point from, Point to) {
        return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
    }

    int[][] parse(String input) {
        String[] strings = input.split(REGEX_NEW_LINE);
        int sizeY = strings.length;
        int sizeX = strings[0].length();
        int[][] square = new int[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                square[y][x] = strings[y].charAt(x);
            }
        }
        return square;
    }

    static List<Point> neighbors(Point p, Direction d, int[][] square, int blocks) {
        int sizeY = square.length;
        int sizeX = square[0].length;
        List<Point> neighbors = new ArrayList<>();
        switch (d) {
            case RIGHT, LEFT -> {
                for (Direction direction : Arrays.asList(Direction.UP, Direction.DOWN)) {
                    int newX = p.x + direction.deltaX;
                    int newY = p.y + direction.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Point(newY, newX));
                    }
                }
                if (blocks < 3) {
                    int newX = p.x + d.deltaX;
                    int newY = p.y + d.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Point(newY, newX));
                    }
                }
            }
            case DOWN, UP -> {
                for (Direction direction : Arrays.asList(Direction.LEFT, Direction.RIGHT)) {
                    int newX = p.x + direction.deltaX;
                    int newY = p.y + direction.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Point(newY, newX));
                    }
                }
                if (blocks < 3) {
                    int newX = p.x + d.deltaX;
                    int newY = p.y + d.deltaY;
                    if (newX >= 0 && newX < sizeX && newY >= 0 && newY < sizeY) {
                        neighbors.add(new Point(newY, newX));
                    }
                }
            }
        }
        return neighbors;
    }

    private static class Node {
        Point point;
        int cost;
        int h;
        Node parent;

        public Node(Point point, int cost, int h, Node parent) {
            this.point = point;
            this.cost = cost;
            this.h = h;
            this.parent = parent;
        }

        public int getF() {
            return cost + h;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return point.equals(node.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(point);
        }
    }
}
