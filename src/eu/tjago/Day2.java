package eu.tjago;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        new Day2().Solution();
    }

    void Solution() {

        List<String> commands =
                Arrays.asList(Common.getStringArraysOutOfFile("res/Day2Data1.txt")
                .get(0).split(","));

        List<Integer> commandValues = commands
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        initReplicateProgramStepsBeforeFailure(commandValues);
        for (int index = 0; index < commandValues.size(); index += 4) {
            if (commandValues.get(index) == 99) {
                System.out.println("Program halted, value at pos 0 is " + commandValues.get(0));
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


        System.out.println(commandValues);
    }

    private void initReplicateProgramStepsBeforeFailure(List<Integer> commandValues) {
        commandValues.set(1, 12);
        commandValues.set(2, 2);
    }
}
