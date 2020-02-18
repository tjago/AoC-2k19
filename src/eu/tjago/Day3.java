package eu.tjago;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        new Day3().solution();
    }

    int [][] grid;

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


        System.out.println(wire2PathSteps);
    }

    private void populateGrid(List<String> wirePathSteps) {

        List<Step> pathSteps = wirePathSteps.stream()
                .map(Step::new).collect(Collectors.toList());

    }
}
