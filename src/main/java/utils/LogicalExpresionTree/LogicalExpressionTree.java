package utils.LogicalExpresionTree;
import utils.LogicalExpresionTree.LogicalToken;
import utils.LogicalExpresionTree.LogicalTokenType;

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

        public boolean evaluate_expr() {
            switch (data) {
                case "&":
                    return left.evaluate_expr() && right.evaluate_expr();
                case "|":
                    return left.evaluate_expr() || right.evaluate_expr();
                case "^":
                    return left.evaluate_expr() ^ right.evaluate_expr();
                case "~":
                    return !right.evaluate_expr();
                default:
                    return data.equals("1");
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
        System.out.println(root);
    }

    /// (1 & 0) | (1 ^ 0)
    private LogicalExpressionTree.Node buildExpressionTree(Queue<LogicalToken> tokens) {
        Stack<LogicalExpressionTree.Node> operandStack = new Stack<>();
        Stack<LogicalExpressionTree.Node> operatorStack = new Stack<>();
        Stack<LogicalExpressionTree.Node> unaryOperatorStack = new Stack<>();

        while(!tokens.isEmpty()){
            LogicalToken token = tokens.poll();
            if (token.isType(LogicalTokenType.OPEN_PARENTHESIS))
            {
                operandStack.push(buildExpressionTree(tokens));
            }
            else if(token.isType(LogicalTokenType.CLOSE_PARENTHESIS)) {
                break;
            }
            else if(token.isType(LogicalTokenType.OPERATOR_UNARIO)) {
                unaryOperatorStack.add(new LogicalExpressionTree.Node(token.getData()));
            }
            else if(token.isType(LogicalTokenType.NUMBER)) {
                operandStack.push(new LogicalExpressionTree.Node(token.getData()));
            }
            else {
                while(!unaryOperatorStack.isEmpty())
                {
                    LogicalExpressionTree.Node operatorNode = unaryOperatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operandStack.push(operatorNode);
                }

                while (!operatorStack.isEmpty() && getOperatorPriority(token.getData()) <= getOperatorPriority(operatorStack.peek().data)) {
                    LogicalExpressionTree.Node operatorNode = operatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operatorNode.left = operandStack.pop();
                    operandStack.push(operatorNode);
                }
                operatorStack.push(new LogicalExpressionTree.Node(token.getData()));
            }
        }

        while(!unaryOperatorStack.isEmpty())
        {
            LogicalExpressionTree.Node operatorNode = unaryOperatorStack.pop();
            operatorNode.right = operandStack.pop();
            operandStack.push(operatorNode);
        }

        while (!operatorStack.isEmpty()) {
            LogicalExpressionTree.Node operatorNode = operatorStack.pop();
            operatorNode.right = operandStack.pop();
            operatorNode.left = operandStack.pop();
            operandStack.push(operatorNode);
        }

        return operandStack.pop();
    }

    private int getOperatorPriority(String operator) {
        if (operator.equals("~") || operator.equals("^")) {
            return 3;
        } else if (operator.equals("&")) {
            return 2;
        } else if (operator.equals("|")) {
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


    public static Queue<LogicalToken> Tokenizer(String expression){
        LinkedList<LogicalToken> linkedList = new LinkedList<>();
        for (char c : expression.toCharArray()){
            if (c == '&' ||  c == '|'|| c== '^' ) {
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.OPERATOR_BINARIO);
                linkedList.add(token);
            }
            if (c == '~') {
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.OPERATOR_UNARIO);
                linkedList.add(token);
            }
            if (c == '(' ){
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.OPEN_PARENTHESIS);
                linkedList.add(token);
            }
            if (c == ')'){
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.CLOSE_PARENTHESIS);
                linkedList.add(token);
            }
            if (c == '1'|| c== '0'){
                LogicalToken token = new LogicalToken(Character.toString(c), LogicalTokenType.NUMBER);
                linkedList.add(token);
            }
        }
        return linkedList;
    }


    public boolean evaluate() {
        return root.evaluate_expr();
    }

}

