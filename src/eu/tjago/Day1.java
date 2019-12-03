package eu.tjago;

import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;

public class Day1 {
    public static void main(String[] args) {
        new Day1().Solution();
    }

    void Solution() {
        List<String> inputs = Common.getStringArraysOutOfFile("res/Day1Data1.txt");
//        inputs.stream().forEach(System.out::println);

        OptionalInt result = inputs.stream()
                .map(Integer::parseInt)
                .mapToInt(Day1::calculateMassByRecursion)
                .reduce((left, right) -> left + right);

        result.ifPresent(System.out::println);
        }

    static Integer calculateMass(Integer i) {
        if (i < 6) return 0;
        return i / 3 - 2;
    }

    static Integer calculateMassByRecursion(Integer mass) {
        if (mass > 0) {
            return calculateMass(mass) + calculateMassByRecursion(calculateMass(mass));
        } else {
            return 0;
        }
    }
}
