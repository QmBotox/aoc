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
        int n = 0;
//        for (int i = 0; i < bricks.size(); i++) {
//            List<Brick> newBricks = new ArrayList<>(trueResult);
//            newBricks.remove(i);
//            if (newBricks.equals(fall(newBricks))) n++;
//        }
        for (Brick b : supportTo.keySet()) {
            boolean canDisintegrate = true;
            for (Brick brick : supportTo.get(b)) {
                int supporters = 0;
                for (List<Brick> bricksList : supportTo.values()){
                    if (bricksList.contains(brick)) supporters++;
                }
                if (supporters < 2) {
                    canDisintegrate = false;
                    break;
                }
            }
            if (canDisintegrate) n++;
        }
        return n;
    }

    @Override
    public Object part2(String input) {
        return null;
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
                int i = 0;
                if (brick.start.z == b.end.z + 1 && brick.intersects(b)) {
                    bricksUp.add(brick);
                }
            }
            support.put(b, bricksUp);
        }
        return support;
    }
    int useless2(List<Brick> bricks) {
        int useless = 0;
        Set<Brick> uselessSet = new HashSet<>();
        for (Brick b : bricks) {
            List<Brick> bricksDown = new ArrayList<>();
            for (Brick brick : bricks) {
                if (brick.start.z == b.start.z - 1 && b.intersects(brick)) {
                    bricksDown.add(brick);
                }
            }
            if (bricksDown.size() > 1) {
                uselessSet.addAll(bricksDown);
            }
        }
        for (Brick b : bricks) {
            List<Brick> bricksUp = new ArrayList<>();
            for (Brick brick : bricks) {
                if (brick.start.z == b.start.z + 1 && b.intersects(brick)) {
                    bricksUp.add(brick);
                }
            }
            if (bricksUp.size() == 0) uselessSet.add(b);
        }
        return uselessSet.size();
    }

    List<Brick> fall(List<Brick> bricks) {
        List<Brick> newBricks = new ArrayList<>();
        for (Brick b : bricks) {
            newBricks.add(b.fall(newBricks));
        }
        return newBricks;
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

        boolean intersects(Brick brick){
            return (((brick.start.y >= start.y && brick.start.y <= end.y)
                    || (brick.end.y >= start.y && brick.end.y <= end.y)
                    || (end.y >= brick.start.y && end.y <= brick.end.y)
                    || (start.y >= brick.start.y && start.y <= brick.end.y))
                    && ((brick.start.x >= start.x && brick.start.x <= end.x)
                    || (brick.end.x >= start.x && brick.end.y <= end.x)
                    || (end.x >= brick.start.x && end.x <= brick.end.x)
                    || (start.x >= brick.start.x && start.x <= brick.end.x))) ;
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
