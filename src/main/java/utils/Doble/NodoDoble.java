package utils.Doble;
/**
 * Clase NodoDoble que implementa un nodo para una lista doblemente enlazada.
 * Cada nodo contiene un valor de tipo T y punteros al nodo anterior y al siguiente en la lista.
 */
public class NodoDoble<T> {

    // Representa el valor del nodo
    T data;

    // Representa el nodo que está antes
    NodoDoble previous;

    // Representa el nodo que sigue
    NodoDoble next;

    /**
     * Constructor de la clase NodoDoble.
     * Inicializa el nodo con el valor proporcionado y establece los punteros al nodo anterior y al siguiente como null.
     *
     * @param data El valor del nodo.
     */
    public NodoDoble(T data){
        this.data = data;
        next = null;
        previous = null;
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
     * Obtiene el nodo anterior.
     *
     * @return El nodo anterior.
     */
    public NodoDoble<T> getPrevious() {
        return previous;
    }

    /**
     * Establece el nodo que está antes.
     *
     * @param previous El nuevo nodo anterior.
     */
    public void setPrevious(NodoDoble previous) {
        this.previous = previous;
    }

    /**
     * Obtiene el nodo siguiente.
     *
     * @return El nodo siguiente.
     */
    public NodoDoble<T> getNext() {
        return next;
    }

    /**
     * Establece el nodo que sigue.
     *
     * @param next El nuevo nodo siguiente.
     */
    public void setNext(NodoDoble next) {
        this.next = next;
    }

}
