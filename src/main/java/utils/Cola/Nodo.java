package utils.Cola;

/**
 * Clase Nodo que implementa un nodo genérico para una lista enlazada.
 * Cada nodo contiene un valor de tipo T y un puntero al siguiente nodo en la lista.
 */
public class Nodo<T> {
    // Representa el valor del nodo
    private T data;
    // Representa el siguiente nodo en la lista enlazada
    private Nodo<T> next;

    /**
     * Constructor de la clase Nodo.
     * Inicializa el nodo con el valor proporcionado y establece el siguiente nodo como null.
     *
     * @param data El valor del nodo.
     */
    public Nodo(T data) {//De momento hago el nodo para int porque no se con que se va trabajar todavia
        this.data = data;
        next = null;
    }

    /**
     * Obtiene el valor del nodo.
     *
     * @return El valor del nodo.
     */
    public T getData() {
        return data;
    }

    /**
     * Establece el valor del nodo.
     *
     * @param data El nuevo valor del nodo.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Obtiene el siguiente nodo en la lista enlazada.
     *
     * @return El siguiente nodo en la lista enlazada.
     */
    public Nodo<T> getNext() {
        return next;
    }

    /**
     * Establece el siguiente nodo en la lista enlazada.
     *
     * @param next El nuevo siguiente nodo en la lista enlazada.
     */
    public void setNext(Nodo<T> next) {
        this.next = next;
    }
}
