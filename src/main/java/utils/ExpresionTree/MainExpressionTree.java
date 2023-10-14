package utils.ExpresionTree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static utils.ExpresionTree.ExpressionTree.Tokenizer;

public class MainExpressionTree {

    public static void main(String[] args) {
        String expression1 = "2*(2+8)";
        ExpressionTree tree1 = new ExpressionTree(expression1);
        int result1 = tree1.evaluate();
        System.out.println("Resultado 1: " + result1);


    }
}

