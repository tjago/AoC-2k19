package eu.tjago;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    static Integer inputMin = 254032;
    static Integer inputMax = 789860;

    public static void main(String[] args) {
        new Day4().solution();
    }

    void solution() {

        long solution1 = IntStream.range(inputMin, inputMax +1)
                .filter(Day4::checkIfNumHasPairsAndIsNotDecreasing).count();
        System.out.println("result is " + solution1);
    }

    static boolean checkIfNumHasPairsAndIsNotDecreasing(Integer number) {
        List<Long> numberList = number.toString().chars()
                .mapToLong(value -> 48 - Character.getNumericValue(value)).boxed().collect(Collectors.toList());

        long numberOfAdjacentSameNumberPairs = numberList.stream()
                .filter(val -> checkIfNextValIsSame(val, numberList)).count();

        long numberOfDecreasingNumbers = numberList.stream()
                .filter(val -> checkIfNextNumberIsDecreasing(val, numberList)).count();

        return numberOfAdjacentSameNumberPairs > 0 && numberOfDecreasingNumbers == 0;
    }

    private static boolean checkIfNextNumberIsDecreasing(Long val, List<Long> numberList) {
        ListIterator<Long> iterator = numberList.listIterator(numberList.indexOf(val));
        return val < iterator.next();
    }

    static boolean checkIfNextValIsSame(Long val, List<Long> numList) {
        ListIterator<Long> iterator = numList.listIterator(numList.indexOf(val));
        return val.equals(iterator.next());
    }
}
