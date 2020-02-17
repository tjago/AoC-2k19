package eu.tjago;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    static int inputMin = 254032;
    static int inputMax = 789860;

    public static void main(String[] args) {
        new Day4().solution();
    }

    void solution() {

        long solution1 = IntStream.range(inputMin, inputMax +1)
                .filter(Day4::checkIfNumHasPairsAndIsNotDecreasing).count();
        System.out.println("result is " + solution1);
    }

    static boolean checkIfNumHasPairsAndIsNotDecreasing(Integer number) {
        List<Integer> numberList = number.toString().chars()
                .map(Character::getNumericValue).boxed().collect(Collectors.toList());

        boolean adjacentPair = false;
        for(Integer num : numberList) {
            if (number.toString().contains(num.toString() + num.toString()) &&
                    !number.toString().contains(num.toString() + num.toString() + num.toString())) { //Part 2 condition
                adjacentPair = true;
            }
        }

        //I check if list can be sorted if sorted then it's not in increasing order
        List<Integer> sortedList = numberList.stream().sorted().collect(Collectors.toList());

        return sortedList.equals(numberList) && adjacentPair;
    }
}
