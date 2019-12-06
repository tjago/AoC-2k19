package eu.tjago;

import java.util.*;
import java.util.stream.Collectors;

public class Day2Part2 {
    public static void main(String[] args) {
        new Day2Part2().Solution();
    }

    private void Solution() {

        List<String> commands =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day2Data1.txt")
                .get(0).split(","));

        List<Integer> commandValues = commands
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        checkNounsAndVerbsOfArray(commandValues);

    }

    private Integer calculateResult(List<Integer> commandValues) {

        for (int index = 0; index < commandValues.size(); index += 4) {
            if (commandValues.get(index) == 99) {
                System.out.println("Program halted, value at pos 0 is " + commandValues.get(0));
                return commandValues.get(0);
            }
            int opCodeVal1Index = commandValues.get(index + 1);
            int opCodeVal2Index = commandValues.get(index + 2);
            int opCodeResultIndex = commandValues.get(index + 3);

            switch (commandValues.get(index)) {
                case 1:
                    commandValues.set(opCodeResultIndex, commandValues.get(opCodeVal1Index) + commandValues.get(opCodeVal2Index));
                    break;
                case 2:
                    commandValues.set(opCodeResultIndex, commandValues.get(opCodeVal1Index) * commandValues.get(opCodeVal2Index));
                    break;
            }
        }
        return -1;
    }

    private void checkNounsAndVerbsOfArray(List<Integer> commandValuesOriginal) {
        for(int noun = 0; noun <= 99; noun++) {
            for(int verb = 0; verb <= 99; verb++) {

                List<Integer> commandValues = new ArrayList<>(commandValuesOriginal);

                initReplicateProgramStepsBeforeFailure(commandValues, noun, verb);
                int result = calculateResult(commandValues);
                if (result == 19690720) {
                    System.out.println("found good result, calculating solution: "+ (100 * noun + verb));
                    return;
                }
            }
        }
    }

    private void initReplicateProgramStepsBeforeFailure(List<Integer> commandValues, int noun, int verb) {
        commandValues.set(1, noun);
        commandValues.set(2, verb);
    }
}
