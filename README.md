# Marino Solver
A fast and lightweight expression solver.

To solve your expression, simply create a `com.giuseppepagliaro.marinosolver.parsers.Parser` with your problem.

```java
Parser parser = new Parser("(2+5)*3");
```

Then, inject the parser instance into any Solver subclass and iterate over the steps to get the result.

```java
Solver solver = new ExpressionSolver(parser);

while(solver.hasMoreSteps()) {
    solver.solveStep();
}
```

To display the result, you can request a `java.util.LinkedList` with the steps (in order) and then get the result.

```java
LinkedList<String> exprHistory = solver.getHistory();

for (String step : exprHistory) {
    System.out.println("Step: " + step);
}

System.out.println("Result: " + solver.getResult());
```

This would be the output:

```
Step: (2.0+5.0)*3.0
Step: 7.0*3.0
Result: 21.0
```
