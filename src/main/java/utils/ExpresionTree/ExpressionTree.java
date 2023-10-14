package utils.ExpresionTree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ExpressionTree {
    private Node root;

    public static class Node {
        String data;
        Node left;
        Node right;

        public Node(String data) {
            this.data = data;
        }

        public int evaluate() {
            if (!isOperator(data)) {
                return Integer.parseInt(data);
            }

            int leftValue = left.evaluate();
            int rightValue = right.evaluate();

            switch (data) {
                case "+":
                    return leftValue + rightValue;
                case "-":
                    return leftValue - rightValue;
                case "*":
                    return leftValue * rightValue;
                case "**":
                    return (int) Math.pow(leftValue, rightValue);

                case "/":
                    if (rightValue == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return leftValue / rightValue;
                case "%":
                    return leftValue % rightValue;
                default:
                    return 0;
            }
        }

        public static boolean isOperator(String token) {
            return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%") || token.equals("**");
        }
        public static boolean isOpenParenthesis(String token) {
            return token.equals("(");
        }

        public static boolean isCloseParenthesis(String token) {
            return token.equals(")");
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data='" + data + '\'' +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    /*

6*30+1

0 1
6 * -> Numero

1 2
* 3 -> Operador *

2 3
3 0 -> Numero 3

3 4
0 + -> Numero 30

4 5
+ 1 -> Operador +

5 6
1 _ -> Numero 1


    * */
    public ExpressionTree(String expression) {
        Queue<Token> tokens= Tokenizer(expression);
        root = buildExpressionTree(tokens);
    }

    private Node buildExpressionTree(Queue<Token> tokens) {
        Stack<Node> operandStack = new Stack<>();
        Stack<Node> operatorStack = new Stack<>();

        while(!tokens.isEmpty()){
            Token token = tokens.poll();
            if (token.isType(TokenType.OPEN_PARENTHESIS))
            {
                operandStack.push(buildExpressionTree(tokens));
            }
            else if(token.isType(TokenType.CLOSE_PARENTHESIS)) {
                break;
            }
            else if(token.isType(TokenType.NUMBER)) {
                operandStack.push(new Node(token.getData()));
            } else {
                while (!operatorStack.isEmpty() && getOperatorPriority(token.getData()) <= getOperatorPriority(operatorStack.peek().data)) {
                    Node operatorNode = operatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operatorNode.left = operandStack.pop();
                    operandStack.push(operatorNode);
                }
                operatorStack.push(new Node(token.getData()));
            }
        }

        while (!operatorStack.isEmpty()) {
            Node operatorNode = operatorStack.pop();
            operatorNode.right = operandStack.pop();
            operatorNode.left = operandStack.pop();
            operandStack.push(operatorNode);
        }

        return operandStack.pop();
    }

    private int getOperatorPriority(String operator) {
        if (operator.equals("*") || operator.equals("/") || operator.equals("%") || operator.equals("**")) {
            return 2;
        } else if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else {
            return 0;
        }
    }

    public void printExpression() {
        printExpression(root);
    }

    private void printExpression(Node node) {
        if (node != null) {
            printExpression(node.left);
            System.out.print(" " + node.data);
            printExpression(node.right);
        }
    }
    public static Queue<Token> Tokenizer( String expression){
        LinkedList<Token> linkedList = new LinkedList<>();
        for (char c : expression.toCharArray()){
            if (c == '+' || c == '-' || c == '*' ||  c == '/' ||  c == '%' ) {
                Token token = new Token(Character.toString(c), TokenType.OPERATOR);
                linkedList.add(token);
            }
            if (c == '(' ){
                Token token = new Token(Character.toString(c), TokenType.OPEN_PARENTHESIS);
                linkedList.add(token);
            }
            if (c == ')'){
                Token token = new Token(Character.toString(c), TokenType.CLOSE_PARENTHESIS);
                linkedList.add(token);
            }
            if (Character.isDigit(c)){
                if(linkedList.peekLast() != null && linkedList.peekLast().isType(TokenType.NUMBER))
                {
                    linkedList.peekLast().addChar(c);
                }
                else
                {
                    Token token = new Token(Character.toString(c), TokenType.NUMBER);
                    linkedList.add(token);
                }

            }
            else { Token token = new Token(Character.toString(c), TokenType.OPERATOR);

            }
        }
        return linkedList;
    }

    public int evaluate() {
        return root.evaluate();
    }
}