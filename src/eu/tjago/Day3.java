package eu.tjago;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        new Day3().solution();
    }

    enum Direction { LEFT, RIGHT, UP, DOWN }

    class SegmentPair {
        Segment one, two;

        SegmentPair(Segment a, Segment b) {
            this.one = a;
            this.two = b;
        }
    }

    class Segment {
        Point A, B;

        Segment(Point a, Point b) {
            A = a;
            B = b;
        }

        int length() {
            if (A.x == B.x) {
                return Math.abs(A.y - B.y);
            } else {

                return Math.abs(A.x - B.x);
            }
        }

        int lengthToPoint(Point p) {
            if (A.x == p.x) {
                return Math.abs(A.y - p.y);
            } else {

                return Math.abs(A.x - p.x);
            }
        }

        boolean intersects(Segment other) {
            if (this.A.equals(other.A) || this.A.equals(other.B) || this.B.equals(other.A) || this.B.equals(other.B)) {
                return false;
            }
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

        List<Segment> path1Segments = createSegments(wire1PathSteps);
        List<Segment> path2Segments = createSegments(wire2PathSteps);

        List<SegmentPair> intersectingSegments =
                path1Segments
                        .stream()
                        .flatMap(Segment1 -> path2Segments
                                .stream()
                                .map(Segment2 -> this.checkAndCreateIntersectingSegmentPair(Segment1, Segment2))
                                .filter(Optional::isPresent)
                                .map(Optional::get))
                        .collect(Collectors.toList());

        //Part 1 Solution
        List<Point> intersectionPoints = getClosestIntersectionDistanceToOrigin(intersectingSegments);
        OptionalInt result = shortestManhattanDistance(intersectionPoints);

        System.out.println("Shortest distance for intersecting wires: "
                + ((result.isPresent()) ? result.getAsInt() : " no intersection found"));

        //Part 2 Solution
        result = intersectingSegments
                .stream()
                .mapToInt(spair -> getTotalDistance(spair, path1Segments, path2Segments))
                .peek(System.out::println)
                .min();

        System.out.println("Intersection with lowest number of steps: "
                + ((result.isPresent()) ? result.getAsInt() : " no intersection found"));
    }

    private Optional<SegmentPair> checkAndCreateIntersectingSegmentPair(Segment A, Segment B) {
        return A.intersects(B) ? Optional.of(new SegmentPair(A, B)) : Optional.empty();
    }

    private int getTotalDistance(SegmentPair intersectingSegments, List<Segment> segments1, List<Segment> segments2) {

        Point point = findIntersectionPoint(intersectingSegments);

        return    intersectingSegments.one.lengthToPoint(point)
                + intersectingSegments.two.lengthToPoint(point)
                + sumOfSegmentsPriorSegment(segments1, intersectingSegments.one)
                + sumOfSegmentsPriorSegment(segments2, intersectingSegments.two);
    }

    private int sumOfSegmentsPriorSegment(List<Segment> segmentList, Segment segment){
        return segmentList.subList(0, segmentList.indexOf(segment))
                .stream()
                .mapToInt(Segment::length)
                .sum();
    }

    private List<Point> getClosestIntersectionDistanceToOrigin(List<SegmentPair> intersectingLines) {
        return intersectingLines.stream()
                .map(this::findIntersectionPoint)
                .collect(Collectors.toList());
    }

    //Only works with perpendicular segments!
    private Point findIntersectionPoint(SegmentPair segmentPair) {
        int x = (segmentPair.one.A.x == segmentPair.one.B.x) ? segmentPair.one.A.x : segmentPair.two.A.x;
        int y = (segmentPair.one.A.y == segmentPair.one.B.y) ? segmentPair.one.A.y : segmentPair.two.A.y;

        return new Point(x, y);
    }

    private OptionalInt shortestManhattanDistance(List<Point> intersectionPoints) {

       return intersectionPoints
               .stream()
               .mapToInt(p -> Math.abs(p.x) + Math.abs(p.y))
               .min();

    }

    private List<Segment> createSegments(List<String> wirePathSteps) {

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

        Iterator<Point> iter = points.stream().iterator();
        return points
                .stream()
                .skip(1)
                .map(point -> new Segment(iter.next(), point))
                .collect(Collectors.toList());
    }
}
