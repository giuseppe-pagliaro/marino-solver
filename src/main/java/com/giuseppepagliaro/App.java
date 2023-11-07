package com.giuseppepagliaro;

import java.util.Scanner;

import com.giuseppepagliaro.parsers.ObjectOrientedParser;
import com.giuseppepagliaro.solvers.ExpressionSolver;
import com.giuseppepagliaro.solvers.Solver;

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

        Solver solver = new ExpressionSolver(new ObjectOrientedParser(expression), false);

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }

        System.out.println("Result: " + solver.getResult());
    }

    private static void printUsageStr() {
        throw new IllegalArgumentException("usage: Evaler [string_to_evaluate]");
    }
}
