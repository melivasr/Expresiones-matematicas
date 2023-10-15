package utils.ExpresionTree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static utils.ExpresionTree.ExpressionTree.Tokenizer;

public class MainExpressionTree {

    public static void main(String[] args) {
        String expression1 = "10**10";
        ExpressionTree tree2 = new ExpressionTree(expression1);
        int result2 = tree2.evaluate();
        System.out.println("Resultado 1: " + result2);


    }
}

