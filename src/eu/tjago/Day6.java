package eu.tjago;

import java.util.*;
import java.util.regex.Pattern;

public class Day6 {
    public static void main(String[] args) {
        new Day6().solution();
    }

    private void solution() {

        List<String> inputs = Common.getStringArraysOutOfFile("res/Day6Data.txt");

        Map<String, List<String>> orbits = new HashMap<>();
                inputs.stream().forEach(
                s -> {
                    String[] orbs = s.split(Pattern.quote(")"));
                    if (orbits.get(orbs[0]) != null) {
                        orbits.get(orbs[0]).add(orbs[1]);
                    } else {
                        orbits.put(orbs[0], new ArrayList<>());
                    }
                }
        );
        System.out.println(orbits);
    }
}
