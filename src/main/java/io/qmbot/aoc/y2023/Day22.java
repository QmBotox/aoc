package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;

public class Day22 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Brick> bricks = parse(input);
        bricks = bricks.stream().sorted((Comparator.comparingInt(o -> o.start.z))).toList();
        List<Brick> trueResult = fall(bricks);
        Map<Brick, List<Brick>> supportTo = support(trueResult);
        Map<Brick, List<Brick>> sup = supporters(trueResult);
        Set<Brick> nn = new HashSet<>();
        for (Brick b : supportTo.keySet()) {
            boolean canDisintegrate = true;
            for (Brick brick : supportTo.get(b)) {
                if (sup.get(brick).size() < 2) {
                    canDisintegrate = false;
                    break;
                }
            }
            if (canDisintegrate) {
                nn.add(b);
            }
        }
        return nn.size();
    }

    @Override
    public Object part2(String input) {
        List<Brick> bricks = parse(input);
        bricks = bricks.stream().sorted((Comparator.comparingInt(o -> o.start.z))).toList();
        List<Brick> trueResult = fall(bricks);
        int sum = 0;
        for (Brick b : trueResult) {
            List<Brick> bricksAfterDelB = new ArrayList<>(trueResult.stream().toList());
            bricksAfterDelB.remove(b);
            sum += fallInt(bricksAfterDelB);
        }
        return sum;
    }

    List<Brick> parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(s -> {
            String[] blocks = s.split("~");
            String[] start = blocks[0].split(",");
            String[] end = blocks[1].split(",");
            return new Brick(
                    new Point(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(start[2])),
                    new Point(Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]))
            );
        }).toList();
    }

    Map<Brick, List<Brick>> support(List<Brick> bricks) {
        Map<Brick, List<Brick>> support = new HashMap<>();
        for (Brick b : bricks) {
            List<Brick> bricksUp = new ArrayList<>();
            for (Brick brick : bricks) {
                if (brick.start.z == b.end.z + 1 && brick.intersects(b)) {
                    bricksUp.add(brick);
                }
            }
            support.put(b, bricksUp);
        }
        return support;
    }

    Map<Brick, List<Brick>> supporters(List<Brick> bricks) {
        Map<Brick, List<Brick>> supporters = new HashMap<>();
        for (Brick b : bricks) {
            List<Brick> bricksDown = new ArrayList<>();
            for (Brick brick : bricks) {
                if (brick.end.z + 1 == b.start.z && brick.intersects(b)) {
                    bricksDown.add(brick);
                }
            }
            supporters.put(b, bricksDown);
        }
        return supporters;
    }

    List<Brick> fall(List<Brick> bricks) {
        List<Brick> newBricks = new ArrayList<>();
        for (Brick b : bricks) {
            newBricks.add(b.fall(newBricks));
        }
        return newBricks;
    }

    int fallInt(List<Brick> bricks) {
        List<Brick> newBricks = new ArrayList<>();
        int i = 0;
        for (Brick b : bricks) {
            Brick newBrick = b.fall(newBricks);
            newBricks.add(newBrick);
            if (!b.equals(newBrick)) {
                i++;
            }
        }
        return i;
    }

    static class Brick {
        Point start;
        Point end;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Brick brick = (Brick) o;

            if (!Objects.equals(start, brick.start)) return false;
            return Objects.equals(end, brick.end);
        }

        @Override
        public int hashCode() {
            int result = start != null ? start.hashCode() : 0;
            result = 31 * result + (end != null ? end.hashCode() : 0);
            return result;
        }

        public Brick(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        boolean intersects(Brick brick) {
            return (Math.max(brick.start.y, start.y) <= Math.min(brick.end.y, end.y)
                    && Math.max(brick.start.x, start.x) <= Math.min(brick.end.x, end.x));
        }

        Brick fall(List<Brick> bricks) {
            List<Brick> sorted = bricks.stream().sorted((Comparator.comparingInt((Brick o) -> o.end.z).reversed())).toList();
            for (Brick b : sorted) {
                if (intersects(b)) {
                    int z = b.end.z;
                    int dz = start.z - z - 1;
                    return new Brick(new Point(start.x, start.y, start.z - dz),
                            new Point(end.x, end.y, end.z - dz));
                }
            }
            int dz = start.z - 1;
            return new Brick(new Point(start.x, start.y, start.z - dz),
                    new Point(end.x, end.y, end.z - dz));
        }

        @Override
        public String toString() {
            return "Brick{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    static class Point {
        int x;
        int y;
        int z;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            if (y != point.y) return false;
            return z == point.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return x + ", " + y + ", " + z;
        }
    }
}
