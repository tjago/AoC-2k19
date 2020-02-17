package eu.tjago;

import java.util.Arrays;
import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        new Day3().solution();
    }

    int [][] grid;

    private void solution() {
        List<String> wire1PathSteps =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day3Data1.txt")
                        .get(0).split(","));

        List<String> wire2PathSteps =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day3Data1.txt")
                        .get(1).split(","));

        populateGrid(wire1PathSteps);


        System.out.println(wire2PathSteps);
    }

    private void populateGrid(List<String> wire1PathSteps) {
    }
}
