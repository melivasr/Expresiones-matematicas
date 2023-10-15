package utils.ExpresionTree;

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

        public long evaluate_expr() {
            if (!isOperator(data)) {
                return Integer.parseInt(data);
            }

            long leftValue = left == null ? 0 : left.evaluate_expr();
            long rightValue = right.evaluate_expr();

            switch (data) {
                case "+":
                    return leftValue + rightValue;
                case "-":
                    return leftValue - rightValue;
                case "*": // falta multiplicar dos negativos
                    return leftValue * rightValue;
                case "**":
                    return (long) Math.pow(leftValue, rightValue);

                case "/": //falta dividir dos negativos
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

    public ExpressionTree(String expression) {
        Queue<Token> tokens= Tokenizer(expression);

        root = buildExpressionTree(tokens);
        System.out.println(root);
    }

    private Node buildExpressionTree(Queue<Token> tokens) {
        Stack<Node> operandStack = new Stack<>();
        Stack<Node> operatorStack = new Stack<>();
        Stack<Node> unaryOperatorStack = new Stack<>();

        while(!tokens.isEmpty()){
            Token token = tokens.poll();
            if (token.isType(TokenType.OPEN_PARENTHESIS))
            {
                operandStack.push(buildExpressionTree(tokens));
            }
            else if(token.isType(TokenType.CLOSE_PARENTHESIS)) {
                break;
            }
            else if(token.isType(TokenType.OPERATOR_UNARIO)) {
                unaryOperatorStack.add(new Node(token.getData()));
            }
            else if(token.isType(TokenType.NUMBER)) {
                operandStack.push(new Node(token.getData()));
            }
            else {
                while(!unaryOperatorStack.isEmpty())
                {
                    Node operatorNode = unaryOperatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operandStack.push(operatorNode);
                }

                while (!operatorStack.isEmpty() && getOperatorPriority(token.getData()) <= getOperatorPriority(operatorStack.peek().data)) {
                    Node operatorNode = operatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operatorNode.left = operandStack.pop();
                    operandStack.push(operatorNode);
                }
                operatorStack.push(new Node(token.getData()));
            }
        }

        while(!unaryOperatorStack.isEmpty())
        {
            Node operatorNode = unaryOperatorStack.pop();
            operatorNode.right = operandStack.pop();
            operandStack.push(operatorNode);
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
        if (operator.equals("**")){
            return 3;
        } else if (operator.equals("*") || operator.equals("/") || operator.equals("%")) {
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
            if (c == '/' ||  c == '%' ) {
                Token token = new Token(Character.toString(c), TokenType.OPERATOR_BINARIO);
                linkedList.add(token);
            }
            if (c == '-' || c == '+' ) {

                if (linkedList.isEmpty() || linkedList.peekLast().isType(TokenType.OPEN_PARENTHESIS) || linkedList.peekLast().isType(TokenType.OPERATOR_BINARIO) || linkedList.peekLast().isType(TokenType.OPERATOR_UNARIO)) {
                    Token token = new Token(Character.toString(c), TokenType.OPERATOR_UNARIO);
                    linkedList.add(token);
                } else {
                    Token token = new Token(Character.toString(c), TokenType.OPERATOR_BINARIO);
                    linkedList.add(token);
                }
            }
            if (c == '*') {
                if (linkedList.peekLast() != null && linkedList.peekLast().isType(TokenType.OPERATOR_BINARIO)) {
                    linkedList.peekLast().addChar(c);
                } else {
                    Token token = new Token(Character.toString(c), TokenType.OPERATOR_BINARIO);
                    linkedList.add(token);
                }
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
        }
        return linkedList;
    }

    public long evaluate() {
        return root.evaluate_expr();
    }
}