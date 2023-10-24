package utils.LogicalExpresionTree;
import utils.LogicalExpresionTree.LogicalToken;
import utils.LogicalExpresionTree.LogicalTokenType;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Esta clase representa un árbol de expresión lógica.
 * @author Melissa Vásquez
 */
public class LogicalExpressionTree {
    private Node root;

    /**
     * Esta clase representa un nodo en el árbol de expresión lógica.
     */
    public static class Node {
        String data;
        Node left;
        Node right;

        /**
         * Constructor de la clase Node.
         * @param data datos del nodo
         */
        public Node(String data) {
            this.data = data;
        }
        /**
         * Evalúa la expresión lógica almacenada en el nodo.
         * @return resultado de la evaluación
         */
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

        /**
         * Verifica si un token es un operador.
         * @param token token a verificar
         * @return true si es un operador, false en caso contrario
         */
        public static boolean isOperator(String token) {
            return token.equals("&") || token.equals("|") || token.equals("^") || token.equals("~");
        }
        /**
         * Verifica si un token es un paréntesis abierto.
         * @param token token a verificar
         * @return true si es un paréntesis abierto, false en caso contrario
         */
        public static boolean isOpenParenthesis(String token) {
            return token.equals("(");
        }
        /**
         * Verifica si un token es un paréntesis cerrado.
         * @param token token a verificar
         * @return true si es un paréntesis cerrado, false en caso contrario
         */
        public static boolean isCloseParenthesis(String token) {
            return token.equals(")");
        }
        /**
         * Sobrescribe el método toString para obtener una representación en cadena del nodo.
         * @return cadena representativa del nodo
         */
        @Override
        public String toString() {
            return "Node{" +
                    "data='" + data + '\'' +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
    /**
     * Constructor de la clase LogicalExpressionTree.
     * Construye un árbol de expresiones a partir de la expresión dada.
     *
     * @param expression Expresión a convertir en un árbol de expresiones.
     */
    public LogicalExpressionTree(String expression) {
        Queue<LogicalToken> tokens = Tokenizer(expression);
        root = buildExpressionTree(tokens);
        System.out.println(root);
    }

    /**
     * Construye un árbol de expresión a partir de una cola de tokens lógicos.
     *
     * @param tokens Cola de tokens lógicos.
     * @return Raíz del árbol de expresión.
     */
    private LogicalExpressionTree.Node buildExpressionTree(Queue<LogicalToken> tokens) {
        // Inicializa las pilas de operandos, operadores y operadores unarios.
        Stack<LogicalExpressionTree.Node> operandStack = new Stack<>();
        Stack<LogicalExpressionTree.Node> operatorStack = new Stack<>();
        Stack<LogicalExpressionTree.Node> unaryOperatorStack = new Stack<>();

        while(!tokens.isEmpty()){
            LogicalToken token = tokens.poll();
            if (token.isType(LogicalTokenType.OPEN_PARENTHESIS)) {
                // Si el token es un paréntesis abierto, construye un subárbol de expresión.
                operandStack.push(buildExpressionTree(tokens));
            }
            else if(token.isType(LogicalTokenType.CLOSE_PARENTHESIS)) {
                // Si el token es un paréntesis cerrado, rompe el ciclo.
                break;
            }
            else if(token.isType(LogicalTokenType.OPERATOR_UNARIO)) {
                // Si el token es un operador unario, lo añade a la pila de operadores unarios.
                unaryOperatorStack.add(new LogicalExpressionTree.Node(token.getData()));
            }
            else if(token.isType(LogicalTokenType.NUMBER)) {
                // Si el token es un número, lo añade a la pila de operandos.
                operandStack.push(new LogicalExpressionTree.Node(token.getData()));
            }
            else {
                // Si el token es un operador binario, procesa los operadores unarios y los operadores binarios de la pila.
                while(!unaryOperatorStack.isEmpty()) {
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

        // Procesa los operadores unarios restantes.
        while(!unaryOperatorStack.isEmpty()) {
            LogicalExpressionTree.Node operatorNode = unaryOperatorStack.pop();
            operatorNode.right = operandStack.pop();
            operandStack.push(operatorNode);
        }

        // Procesa los operadores binarios restantes.
        while (!operatorStack.isEmpty()) {
            LogicalExpressionTree.Node operatorNode = operatorStack.pop();
            operatorNode.right = operandStack.pop();
            operatorNode.left = operandStack.pop();
            operandStack.push(operatorNode);
        }

        // Devuelve la raíz del árbol de expresión.
        return operandStack.pop();
    }

    /**
     * Método para obtener la prioridad del operador.
     *
     * @param operator el operador para el que se quiere obtener la prioridad.
     * @return la prioridad del operador.
     */
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

    /**
     * Método para imprimir la expresión.
     */
    public void printExpression() {
        printExpression(root);
    }

    /**
     * Método privado para imprimir la expresión.
     *
     * @param node el nodo desde el que se quiere imprimir la expresión.
     */
    private void printExpression(Node node) {
        if (node != null) {
            printExpression(node.left);
            System.out.print(" " + node.data);
            printExpression(node.right);
        }
    }

    /**
     * Método para tokenizar una expresión.
     *
     * @param expression la expresión a tokenizar.
     * @return una cola de tokens lógicos.
     */
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


    /**
     * Método para evaluar la expresión.
     *
     * @return el resultado de la evaluación de la expresión.
     */
    public boolean evaluate() {
        return root.evaluate_expr();
    }

}

