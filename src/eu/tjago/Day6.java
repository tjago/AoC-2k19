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

        //Part 2

        AtomicInteger minDistance = new AtomicInteger(Integer.MAX_VALUE);
        orbits.forEach((s, strings) -> {
            Distance distToSan = distanceFromObject(s, "SAN" , orbits);
            Distance distToYou = distanceFromObject(s, "YOU" , orbits);
            if (distToSan.isFound() && distToYou.isFound()) {
                int temporaryDistance = distToSan.getDist() + distToYou.getDist() -2; //-2 because we don't count YOU and SAN as orbits
                if (temporaryDistance < minDistance.get()) {
                    minDistance.set(temporaryDistance);
                }
            }
        });
        System.out.println("Minimum distance between Santa and you is: " + minDistance);
    }

    Distance distanceFromObject(String currentObject, String target, Map<String, List<String>> orbits) {
        if (orbits.get(currentObject) == null) {
            return currentObject.equals(target) ? new Distance(true, 0) : new Distance(false, 0);
        } else {
            for (String object : orbits.get(currentObject)) {
                Distance search = distanceFromObject(object, target, orbits);
                if(search.isFound()) {
                    return new Distance(true, search.getDist() + 1);
                }
            }
            return new Distance(false, 0);
        }
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
    class Distance {
        private boolean found = false;
        private int dist = 0;

        public Distance(boolean found, int dist) {
            this.found = found;
            this.dist = dist;
        }

        private void incrementBy(int val) {
            dist+= val;
        }

        public boolean isFound() {
            return found;
        }

        public int getDist() {
            return dist;
        }
    }

}
