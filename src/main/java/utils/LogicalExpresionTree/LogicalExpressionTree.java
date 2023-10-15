package utils.LogicalExpresionTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LogicalExpressionTree {
    private Node root;

    public static class Node {
        String data;
        Node left;
        Node right;

        public Node(String data) {
            this.data = data;
        }

        public boolean evaluate() {
            if (!isOperator(data)) {
                return Boolean.parseBoolean(data);
            }

            boolean leftValue = left.evaluate();
            boolean rightValue = right.evaluate();

            switch (data) {
                case "&": // AND
                    return leftValue && rightValue;
                case "|": // OR
                    return leftValue || rightValue;
                case "^": // XOR
                    return leftValue ^ rightValue;
                case "~": // NOT
                    return !rightValue;
                default:
                    return false;
            }
        }

        public static boolean isOperator(String token) {
            return token.equals("&") || token.equals("|") || token.equals("^") || token.equals("~");
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

    public LogicalExpressionTree(String expression) {
        Queue<LogicalToken> tokens = Tokenizer(expression);
        root = buildExpressionTree(tokens);
    }

    private Node buildExpressionTree(Queue<LogicalToken> tokens) {
        Stack<Node> operandStack = new Stack<>();
        Stack<Node> operatorStack = new Stack<>();

        while (!tokens.isEmpty()) {
            LogicalToken token = tokens.poll();
            if (token.isType(LogicalTokenType.OPEN_PARENTHESIS)) {
                operandStack.push(buildExpressionTree(tokens));
            } else if (token.isType(LogicalTokenType.CLOSE_PARENTHESIS)) {
                break;
            } else if (token.isType(LogicalTokenType.LOGICAL_OPERATOR)) {
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
        if (operator.equals("~")) {
            return 3;  // NOT tiene la mayor prioridad
        } else if (operator.equals("&")) {
            return 2;  // AND tiene una prioridad intermedia
        } else if (operator.equals("|") || operator.equals("^")) {
            return 1;  // OR y XOR tienen la menor prioridad
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

    public static Queue<LogicalToken> Tokenizer(String expression) {
        LinkedList<LogicalToken> linkedList = new LinkedList<>();
        for (char c : expression.toCharArray()) {
            if (c == '&' || c == '|' || c == '^' || c == '~') {
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.LOGICAL_OPERATOR);
                linkedList.add(token);
            }
            if (c == '(') {
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.OPEN_PARENTHESIS);
                linkedList.add(token);
            }
            if (c == ')') {
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.CLOSE_PARENTHESIS);
                linkedList.add(token);
            }
            if (c == '0' || c == '1') {
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.NUMBER);
                linkedList.add(token);
            }
        }
        return linkedList;
    }

    public boolean evaluate() {
        return root.evaluate();
    }

}

