package com.giuseppepagliaro;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String expression = null;

        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Type your expression: ");
            expression = sc.nextLine();

            sc.close();
        }
        else if (args.length != 1) {
            printUsageStr();
        }
        else {
            expression = args[0];
        }

        String result = "prova" + expression; // TODO

        System.out.println("Result: " + result);
    }

    private static void printUsageStr() {
        throw new IllegalArgumentException("usage: Evaler [string_to_evaluate]");
    }
}
