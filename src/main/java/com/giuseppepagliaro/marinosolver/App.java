package com.giuseppepagliaro.marinosolver;

import java.util.Scanner;
import java.util.Iterator;

import com.giuseppepagliaro.marinosolver.exceptions.IncorrectProblemSyntaxException;
import com.giuseppepagliaro.marinosolver.parsers.Parser;
import com.giuseppepagliaro.marinosolver.solvers.ExpressionSolver;
import com.giuseppepagliaro.marinosolver.solvers.Solver;

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

        Solver solver;

        try {
            solver = new ExpressionSolver(new Parser(expression));
        } catch (IncorrectProblemSyntaxException e) {
            System.err.println("Syntax error detected: " + e.getMessage());
            System.exit(-1);
            return;
        }

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }

        Iterator<String> historyIterator = solver.getHistory().iterator();

        while (historyIterator.hasNext()) {
            System.out.println("Step: " + historyIterator.next());
        }

        System.out.println("Result: " + solver.getResult());
    }

    private static void printUsageStr() throws IllegalArgumentException {
        throw new IllegalArgumentException("usage: MarinoSolver [problem_to_evaluate]");
    }
}
