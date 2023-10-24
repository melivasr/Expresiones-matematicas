package utils.ExpresionTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Esta es una clase que representa un árbol de expresiones.
 * Cada nodo en el árbol puede contener un operador o un valor.
 */
public class ExpressionTree {
    private Node root;

    /**
     * Esta es la clase Node que representa un nodo en el árbol de expresiones.
     * Cada nodo tiene un valor (que puede ser un operador o un valor), y dos hijos.
     */
    public static class Node {
        String data;
        Node left;
        Node right;
        /**
         * Constructor de la clase Node que toma un valor para el nodo.
         * @param data el valor del nodo.
         */
        public Node(String data) {
            this.data = data;
        }
        /**
         * Este método evalúa la expresión en el árbol de expresiones.
         * @return el resultado de la expresión.
         */
        public double evaluate_expr() {
            if (!isOperator(data)) {
                return Double.parseDouble(data);
            }

            double leftValue = left == null ? 0 : left.evaluate_expr();
            double rightValue = right.evaluate_expr();

            switch (data) {
                case "+":
                    return leftValue + rightValue;
                case "-":
                    return leftValue - rightValue;
                case "*":
                    return leftValue * rightValue;
                case "**":
                    return Math.pow(leftValue, rightValue);

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
        /**
         * Este método verifica si un token es un operador.
         * @param token el token a verificar.
         * @return true si el token es un operador, false en caso contrario.
         */
        public static boolean isOperator(String token) {
            return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%") || token.equals("**");
        }
        /**
         * Este método verifica si un token es un paréntesis abierto.
         * @param token el token a verificar.
         * @return true si el token es un paréntesis abierto, false en caso contrario.
         */
        public static boolean isOpenParenthesis(String token) {
            return token.equals("(");
        }

        /**
         * Este método verifica si un token es un paréntesis cerrado.
         * @param token el token a verificar.
         * @return true si el token es un paréntesis cerrado, false en caso contrario.
         */
        public static boolean isCloseParenthesis(String token) {
            return token.equals(")");
        }
        /**
         * Este método devuelve una representación en cadena del nodo.
         * @return una representación en cadena del nodo.
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
     * Constructor de la clase ExpressionTree.
     * Construye un árbol de expresiones a partir de la expresión dada.
     *
     * @param expression Expresión a convertir en un árbol de expresiones.
     */
    public ExpressionTree(String expression) {
        Queue<Token> tokens= Tokenizer(expression);

        root = buildExpressionTree(tokens);
        System.out.println(root);
    }

    /**
     * Método para construir un árbol de expresiones a partir de una cola de tokens.
     *
     * @param tokens Cola de tokens a convertir en un árbol de expresiones.
     * @return Nodo raíz del árbol de expresiones construido.
     */
    private Node buildExpressionTree(Queue<Token> tokens) {
        // Inicializamos las pilas que vamos a utilizar
        Stack<Node> operandStack = new Stack<>();
        Stack<Node> operatorStack = new Stack<>();
        Stack<Node> unaryOperatorStack = new Stack<>();

        // Mientras haya tokens en la cola
        while(!tokens.isEmpty()){
            Token token = tokens.poll();
            // Si el token es un paréntesis abierto, construimos un subárbol
            if (token.isType(TokenType.OPEN_PARENTHESIS))
            {
                operandStack.push(buildExpressionTree(tokens));
            }
            // Si el token es un paréntesis cerrado, salimos del bucle
            else if(token.isType(TokenType.CLOSE_PARENTHESIS)) {
                break;
            }
            // Si el token es un operador unario, lo agregamos a la pila de operadores unarios
            else if(token.isType(TokenType.OPERATOR_UNARIO)) {
                unaryOperatorStack.add(new Node(token.getData()));
            }
            // Si el token es un número, lo agregamos a la pila de operandos
            else if(token.isType(TokenType.NUMBER)) {
                operandStack.push(new Node(token.getData()));
            }
            // Si el token es un operador, lo agregamos a la pila de operadores
            else {
                // Primero procesamos los operadores unarios
                while(!unaryOperatorStack.isEmpty())
                {
                    Node operatorNode = unaryOperatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operandStack.push(operatorNode);
                }

                // Luego procesamos los operadores binarios
                while (!operatorStack.isEmpty() && getOperatorPriority(token.getData()) <= getOperatorPriority(operatorStack.peek().data)) {
                    Node operatorNode = operatorStack.pop();
                    operatorNode.right = operandStack.pop();
                    operatorNode.left = operandStack.pop();
                    operandStack.push(operatorNode);
                }
                operatorStack.push(new Node(token.getData()));
            }
        }

        // Después de construir el árbol, procesamos los operadores unarios restantes
        while(!unaryOperatorStack.isEmpty())
        {
            Node operatorNode = unaryOperatorStack.pop();
            operatorNode.right = operandStack.pop();
            operandStack.push(operatorNode);
        }

        // Y finalmente procesamos los operadores binarios restantes
        while (!operatorStack.isEmpty()) {
            Node operatorNode = operatorStack.pop();
            operatorNode.right = operandStack.pop();
            operatorNode.left = operandStack.pop();
            operandStack.push(operatorNode);
        }

        // Devolvemos el nodo raíz del árbol de expresiones
        return operandStack.pop();
    }

    /**
     * Método para obtener la prioridad de un operador.
     *
     * @param operator Operador del que se quiere obtener la prioridad.
     * @return Prioridad del operador.
     */
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

    /**
     * Método para imprimir la expresión representada por el árbol de expresiones.
     */
    public void printExpression() {
        printExpression(root);
    }

    /**
     * Método para imprimir una expresión a partir de un nodo.
     *
     * @param node Nodo desde el que se quiere imprimir la expresión.
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
     * @param expression Expresión a tokenizar.
     * @return Cola de tokens de la expresión.
     */
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
            if (Character.isDigit(c) || c == '.'){
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
    /**
     * Método para evaluar la expresión representada por el árbol de expresiones.
     *
     * @return Resultado de la evaluación de la expresión.
     */
    public double evaluate() {
        return root.evaluate_expr();
    }
}