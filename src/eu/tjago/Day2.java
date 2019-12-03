package eu.tjago;

import java.util.Arrays;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        new Day2().Solution();
    }

    void Solution() {

        List<String> commands =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day2Data1.txt")
                .get(0).split(","));

//        commands.stream().forEach(System.out::println);

        commands.stream()
                .mapToInt(Integer::parseInt)
    }
}
