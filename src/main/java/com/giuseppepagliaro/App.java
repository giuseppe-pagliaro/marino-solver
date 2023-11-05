package com.giuseppepagliaro;

import java.util.Scanner;

import com.giuseppepagliaro.parsers.Parser;

/**
 * Entrypoint for Marino Solver.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
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

        Parser exprPars = new Parser(expression);

        System.out.println("Result: " + exprPars.getProblemTree());
    }

    private static void printUsageStr() {
        throw new IllegalArgumentException("usage: Evaler [string_to_evaluate]");
    }
}
