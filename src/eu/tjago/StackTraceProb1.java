package eu.tjago;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StackTraceProb1 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String inputR = in.nextLine();
        String inputs[] = inputR.split(" ");

        if(inputs.length != 5) {
            System.out.println("Invalid number of input");
        }

        List<Integer> integers = new ArrayList<>();
        for (String s : inputs) {
            Integer number = Integer.parseInt(s);
            integers.add(number);
        }
        System.out.println("Got following numbers in my input: "  + integers);
    }
}
