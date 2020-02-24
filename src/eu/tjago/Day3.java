package eu.tjago;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {
    public static void main(String[] args) {
        new Day3().solution();
    }

    enum Direction { LEFT, RIGHT, UP, DOWN}

    class LinePair {
        Line one, two;

        LinePair(Line a, Line b) {
            this.one = a;
            this.two = b;
        }
    }

    class Line {
        Point A, B;

        public Line(Point a, Point b) {
            A = a;
            B = b;
        }

        boolean intersects(Line other) {
            return Line2D.linesIntersect(this.A.x, this.A.y, this.B.x, this.B.y,
                    other.A.x, other.A.y, other.B.x, other.B.y);
        }
    }
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

    private void solution() {

        List<String> wire1PathSteps =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day3Data1.txt")
                        .get(0).split(","));

        List<String> wire2PathSteps =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day3Data1.txt")
                        .get(1).split(","));

        List<Line> path1Lines = createLines(wire1PathSteps);
        List<Line> path2Lines = createLines(wire2PathSteps);

        List<LinePair> intersectingLines = new ArrayList<>();

        IntStream.range(0, path1Lines.size() - 1).forEach(
                value -> IntStream.range(0, path2Lines.size() - 1).forEach(
                        value1 -> {
                            if (path1Lines.get(value).intersects(path2Lines.get(value1))) {
                                intersectingLines.add(new LinePair(path1Lines.get(value), path2Lines.get(value1)));
                            }
                        }
                )
        );
        System.out.println("Shortest distance for intersecting wires: ");
    }

    private OptionalInt shortestManhattanDistance(List<Point> intersectionPoints) {

       return intersectionPoints
               .stream()
               .mapToInt(p -> Math.abs(p.x) + Math.abs(p.y))
               .min();

    }

    boolean linesIntersect(Point a, Point b, Point c, Point d) {


        return Line2D.linesIntersect(a.x, a.y, b.x, b.y, c.x, c.y, d.x, d.y);
    }

    private List<Line> createLines(List<String> wirePathSteps) {

        List<Step> pathSteps = wirePathSteps
                .stream()
                .map(Step::new)
                .collect(Collectors.toList());

        LinkedList<Point> points = new LinkedList<>();
        points.add(new Point(0,0));

        pathSteps.forEach(step -> {
            Point prevPoint = points.getLast();
                    switch (step.direction) {
                        case UP:
                            points.addLast(new Point(prevPoint.x, prevPoint.y + step.stepsDistance));
                            break;
                        case DOWN:
                            points.addLast(new Point(prevPoint.x, prevPoint.y - step.stepsDistance));
                            break;
                        case LEFT:
                            points.addLast(new Point(prevPoint.x - step.stepsDistance, prevPoint.y));
                            break;
                        case RIGHT:
                            points.addLast(new Point(prevPoint.x + step.stepsDistance, prevPoint.y));
                            break;
                    }
                });


        return IntStream
                .range(0, pathSteps.size() -1)
                .mapToObj(val -> new Line(points.get(0), points.get(+1)))
                .collect(Collectors.toList());
    }
}
