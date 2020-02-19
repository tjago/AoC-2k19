package eu.tjago;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        populateGrid(wire1PathSteps);
        populateGrid(wire2PathSteps);

        long wiresPathCount = IntStream.rangeClosed(0, 10_000).mapToLong(
                x -> IntStream.rangeClosed(0, 10_000).filter(
                        y -> grid[x][y] == 1
                ).count()
        ).sum();

        System.out.println(wiresPathCount);
    }

    private void populateGrid(List<String> wirePathSteps) {

        List<Step> pathSteps = wirePathSteps.stream()
                .map(Step::new).collect(Collectors.toList());

        GridCursor gc = new GridCursor(5_000, 5_000);

        pathSteps.forEach(step -> {
            System.out.println("processing step: " + step);
                    switch (step.direction) {
                        case UP:
                            IntStream.rangeClosed(1, step.stepsDistance)
                                    .forEach(value -> grid[ gc.getCursorX() ][ gc.incCursorY() ]++);
                            break;
                        case DOWN:
                            IntStream.rangeClosed(1, step.stepsDistance)
                                    .forEach(value -> grid[ gc.getCursorX() ][ gc.decCursorY() ]++);
                            break;
                        case LEFT:
                            IntStream.rangeClosed(1, step.stepsDistance)
                                    .forEach(value -> grid[ gc.decCursorX() ][ gc.getCursorY() ]++);
                            break;
                        case RIGHT:
                            IntStream.rangeClosed(1, step.stepsDistance)
                                    .forEach(value -> grid[ gc.incCursorX() ][ gc.getCursorY() ]++);
                            break;
                    }
                });
    }
}
