package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import io.qmbot.aoc.y2023.Day14.Point;

import java.util.ArrayList;
import java.util.List;

public class Day18 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Point> angles = new ArrayList<>();
        int[][] m = parse(input, angles);
        return shoelaceArea(angles);
    }

    @Override
    public Object part2(String input) {
        return null;
    }


    static int[][] parse(String input, List<Point> angles) {
        int sizeY = 0;
        int sizeX = 0;
        for (String s : input.split(REGEX_NEW_LINE)) {
            String[] sParse = s.split(" ");
            char c = s.charAt(0);
            int i = Integer.parseInt(sParse[1]);
            if (c == 'R' || c == 'L') {
                sizeX += i;
            } else if (c == 'D' || c == 'U') {
                sizeY += i;
            }
        }
        int[][] matrix = new int[sizeY *2][sizeX * 2];
        Point p = new Point(matrix.length / 2, matrix[0].length / 2);
        for (String s : input.split(REGEX_NEW_LINE)) {
            angles.add(p);
            p = draw(matrix, p, s);
        }
        return matrix;
    }

    static Point draw(int[][] matrix, Point p, String s) {
        String[] parse = s.split(" ");
        int y = p.y;
        int x = p.x;
        int n = Integer.parseInt(parse[1]);
        switch (s.charAt(0)) {
            case 'U' -> {
                for (int i = 0; i < n; i++) {
                    matrix[y][x] = 1;
                    y--;
                }
            }
            case 'R' -> {
                for (int i = 0; i < n; i++) {
                    matrix[y][x] = 1;
                    x++;
                }
            }
            case 'D' -> {
                for (int i = 0; i < n; i++) {
                    matrix[y][x] = 1;
                    y++;
                }
            }
            case 'L' -> {
                for (int i = 0; i < n; i++) {
                    matrix[y][x] = 1;
                    x--;
                }
            }
        }
        return new Point(y, x);
    }

    static double shoelaceArea(List<Point> angles) {
        int n = angles.size();
        double a = 0.0;
        for (int i = 0; i < n - 1; i++) {
            a += angles.get(i).x * angles.get(i + 1).y - angles.get(i + 1).x * angles.get(i).y;
        }
        return Math.abs(a + angles.get(n - 1).x * angles.get(0).y - angles.get(0).x * angles.get(n - 1).y) / 2.0;
    }

    public static void fill(int[][] matrix, int startX, int startY) {
//        int rows = matrix.length;
//        int cols = matrix[0].length;
//
//        boolean[][] visited = new boolean[rows][cols];
//
//        if (!isValid(matrix, startX, startY, visited)) {
//            return;
//        }
//
//        fillHelper(matrix, startX, startY, visited);
//
//        for (boolean[] booleans : matrix) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print(booleans[j] ? "1 " : "0 ");
//            }
//            System.out.println();
//        }
    }

    private static void fillHelper(boolean[][] matrix, int y, int x, boolean[][] visited) {
        if (!isValid(matrix, x, y, visited)) {
            return;
        }

        visited[x][y] = true;
        matrix[x][y] = true;

        fillHelper(matrix, y, x - 1, visited);
        fillHelper(matrix, y, x + 1, visited);
        fillHelper(matrix, y - 1, x, visited);
        fillHelper(matrix, y + 1, x, visited);
    }

    private static boolean isValid(boolean[][] matrix, int x, int y, boolean[][] visited) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        return x >= 0 && x < rows && y >= 0 && y < cols && !visited[x][y] && !matrix[x][y];
    }
}
