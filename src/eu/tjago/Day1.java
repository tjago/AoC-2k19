package eu.tjago;

import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        new Day1().Solution();
    }

    void Solution() {
        List<String> inputs = Common.getStringArraysOutOfFile("res/Day1Data1.txt");
        inputs.stream().forEach(System.out::println);
    }
}
