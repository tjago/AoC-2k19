package eu.tjago;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Day3 {
    public static void main(String[] args) {
        new Day3().solution();
    }

    int [][] grid = new int[10_000][10_000];

    enum Direction { LEFT, RIGHT, UP, DOWN}

    class Step {
        Direction direction;
        int stepsDistance;

        Step(String s) {
            if (s.contains("L")) {
                direction = Direction.LEFT;
            } else if(s.contains("R")) {
                direction = Direction.RIGHT;
            } else if (s.contains("U")) {
                direction = Direction.UP;
            } else if (s.contains("D")) {
                direction = Direction.DOWN;
            } else {
                throw new IllegalArgumentException("Unknown parameter in step: " + s);
            }

            stepsDistance = Integer.parseInt(s.substring(1));
        }

        @Override
        public String toString() {
            return this.direction + " " + stepsDistance;
        }
    }
    class GridCursor {
        int cursorX;
        int cursorY;

        GridCursor() {
            this.cursorX = 0;
            this.cursorY = 0;
        }
        GridCursor(int startX, int startY) {
            this.cursorX = startX;
            this.cursorY = startY;
        }

        int getCursorX() {
            return this.cursorX;
        }

        int getCursorY() {
            return this.cursorY;
        }

        int incCursorX() {
            return this.cursorX++;
        }
        int incCursorY() {
            return this.cursorY++;
        }
        int decCursorX() {
            return this.cursorX--;
        }
        int decCursorY() {
            return this.cursorY--;
        }

    }

    private void solution() {

        List<String> wire1PathSteps =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day3Data1.txt")
                        .get(0).split(","));

        List<String> wire2PathSteps =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day3Data1.txt")
                        .get(1).split(","));

        LinkedList<Point> path1Points = populateGrid(wire1PathSteps);
        LinkedList<Point> path2Points = populateGrid(wire2PathSteps);


        IntStream.range(0, path1Points.size() - 1).forEach(
                value -> IntStream.range(0, path2Points.size() - 1).filter(
                        value1 -> linesIntersect(path1Points.get(value), path1Points.get(value +1),
                                path2Points.get(value1), path2Points.get(value1 +1))
                ).peek(System.out::println)
        );


//        long wiresPathCount = IntStream.rangeClosed(0, 10_000).mapToLong(
//                x -> IntStream.rangeClosed(0, 10_000).filter(
//                        y -> grid[x][y] == 1
//                ).count()
//        ).sum();

//        System.out.println(wiresPathCount);
    }

    boolean linesIntersect(Point a, Point b, Point c, Point d) {
        return Line2D.linesIntersect(a.x, a.y, b.x, b.y, c.x, c.y, d.x, d.y);
    }

    private LinkedList<Point> populateGrid(List<String> wirePathSteps) {

        List<Step> pathSteps = wirePathSteps.stream()
                .map(Step::new).collect(Collectors.toList());

        GridCursor gc = new GridCursor(5_000, 5_000);

        LinkedList<Point> path1Points = new LinkedList<>();
        path1Points.add(new Point(0,0));

        int cursorX = 0, cursorY = 0;

        pathSteps.forEach(step -> {
            Point prevPoint = path1Points.getLast();
                    switch (step.direction) {
                        case UP:
                            path1Points.addLast(new Point(prevPoint.x, prevPoint.y + step.stepsDistance));
                            break;
                        case DOWN:
                            path1Points.addLast(new Point(prevPoint.x, prevPoint.y - step.stepsDistance));
                            break;
                        case LEFT:
                            path1Points.addLast(new Point(prevPoint.x - step.stepsDistance, prevPoint.y));
                            break;
                        case RIGHT:
                            path1Points.addLast(new Point(prevPoint.x + step.stepsDistance, prevPoint.y));
                            break;
                    }
                });

        return path1Points;
    }
}
