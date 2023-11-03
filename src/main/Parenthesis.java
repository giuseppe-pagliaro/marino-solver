package src.main;

import java.util.ArrayList;

import src.main.errors.ExpressionErrorMessage;

public class Parenthesis {
    private Parenthesis (int start, int end) {
        this.start = start;
        this.end = end;
    }

    private int start;
    private int end;

    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }

    public static Parenthesis findParenthesis(ArrayList<String> tokenizedExpression) {
        int openInd = tokenizedExpression.indexOf("(");
        int closeInd = tokenizedExpression.indexOf(")");

        if (openInd == -1 && closeInd == -1) {
            return null;
        }
        else if (openInd == -1) {
            ExpressionErrorMessage.PARENTHESIS_NOT_CLOSED.printErrorMessage(openInd);
        }
        else if (closeInd == -1) {
            ExpressionErrorMessage.PARENTHESIS_NEVER_OPENED.printErrorMessage(closeInd);
        }

        int nextToken = tokenizedExpression.subList(openInd + 1, tokenizedExpression.size() - 1).indexOf("(");

        if (nextToken + openInd + 1 < closeInd && openInd != -1) {
            int openCount = 2;
            int closeCount = 1;

            while (openCount != closeCount) {
                int nextOpenInd = tokenizedExpression.subList(nextToken + 1, tokenizedExpression.size() - 1).indexOf("(");
                int nextCloseInd = tokenizedExpression.subList(nextToken + 1, tokenizedExpression.size() - 1).indexOf(")");

                if (nextOpenInd == -1 && nextCloseInd == -1)
                    ExpressionErrorMessage.PARENTHESIS_NOT_CLOSED.printErrorMessage(openInd);
                
                if (nextOpenInd < nextCloseInd) {
                    openCount++;
                    nextToken = nextOpenInd;
                }
                else {
                    closeCount++;
                    nextToken = nextCloseInd;
                }
            }

            closeInd = nextToken;
        }
        
        return new Parenthesis(openInd, closeInd);
    }
}
