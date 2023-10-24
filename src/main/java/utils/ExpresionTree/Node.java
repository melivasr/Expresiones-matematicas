package utils.ExpresionTree;

/**
 * Esta clase representa un nodo de la expresión.
 * Cada nodo tiene un valor de tipo String y dos nodos hijos, izquierdo y derecho.
 */
public class Node {
    String data;
    Node left;
    Node right;

    /**
     * Constructor de la clase Node.
     * @param data El valor del nodo.
     */
    public Node(String data) {
        this.data = data;
    }
}
