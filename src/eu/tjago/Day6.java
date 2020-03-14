package eu.tjago;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
                        List<String> orbitArray = new ArrayList<>();
                        orbitArray.add(orbs[1]);
                        orbits.put(orbs[0], orbitArray);
                    }
                }
        );
//                orbits.forEach((s, strings) -> System.out.println("key: " + s + " list: " + strings));

        AtomicInteger counter = new AtomicInteger();
        orbits.forEach((s, strings) -> {
            counter.addAndGet(countOrbitsOf(s,orbits));
        });

        System.out.println("All orbits of objects = " + counter);
    }

    int countOrbitsOf(String currentObject, Map<String, List<String>> orbits) {
        if (orbits.get(currentObject) == null) {
            return 0;
        } else {
            int count = 0;
            for (String object : orbits.get(currentObject)) {
                count += 1 + countOrbitsOf(object, orbits);
            }
            return count;
        }
    }

}
