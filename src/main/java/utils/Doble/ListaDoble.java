package utils.Doble;


import utils.Cola.Nodo;

/**
 * Clase ListaDoble que implementa una lista doblemente enlazada para almacenar elementos de tipo T.
 * Esta lista se utiliza para implementar la malla del juego.
 */
public class ListaDoble<T> {

    // Representa el primer elemento de la lista
    private NodoDoble<T> head;

    public NodoDoble<T> getHead() {
        return head;
    }

    // Representa el último elemento de la lista
    private NodoDoble<T> tail;

    // Representa el largo de la lista
    private int length;

    /**
     * Constructor de la clase ListaDoble.
     * Inicializa la lista vacía.
     */
    public ListaDoble(){
        this.head=null;
        this.tail=null;
        this.length=0;
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return Verdadero si la lista está vacía, falso en caso contrario.
     */
    public boolean isEmpty(){
        return length==0;
    }

    /**
     * Obtiene la longitud de la lista.
     *
     * @return La longitud de la lista.
     */
    public int getLength(){
        return length;
    }

    /**
     * Inserta un nuevo nodo al final de la lista.
     *
     * @param value El valor a insertar en el nuevo nodo.
     */
    public void insertLast(T value){
        NodoDoble<T> newNode = new NodoDoble<T>(value);
        if(isEmpty()){
            head=newNode;
            tail=newNode;
        }else{
            tail.setNext(newNode);
        }
        newNode.setPrevious(tail);
        tail=newNode;
        length++;
    }

    /**
     * Inserta un nuevo nodo al principio de la lista.
     *
     * @param value El valor a insertar en el nuevo nodo.
     */
    public void insertFirst(T value){
        NodoDoble<T> newNode = new NodoDoble<T>(value);
        if(isEmpty()) {
            tail = newNode;
            head = newNode;
        }else{
            head.setPrevious(newNode);
        }
        newNode.setNext(head);
        head=newNode;
        length++;
    }

    public NodoDoble<T> getTail() {
        return this.tail;
    }

    /**
     * Elimina el primer nodo de la lista.
     */
    public void deleteFirst(){
        if(isEmpty()){
            System.out.println("Lista vacia");
        }
        NodoDoble temp=head;
        if(head==tail){
            tail=null;
            head=null;
        }else {
            head.getNext().setPrevious(null);
        }
        head=head.getNext();
        temp.setNext(null);
        length--;
        System.out.println("Se elimino "+temp.getData());
    }

    /**
     * Elimina el último nodo de la lista.
     */
    public void deleteLast(){
        if(isEmpty()){
            System.out.println("Lista vacia");
        }
        NodoDoble temp=tail;
        if(head==tail){
            tail=null;
            head=null;
        }else {
            tail.getPrevious().setNext(null);
        }
        tail=tail.getPrevious();
        temp.setPrevious(null);
        length--;
        System.out.println("Se elimino "+temp.getData());
    }

    /**
     * Imprime los elementos de la lista en orden.
     */
    public void displayForward(){
        if(head==null){
            return;
        }
        NodoDoble<T> temp=head;
        System.out.print("[");
        while (temp != null) {
            if(temp.getNext()!=null) {
                System.out.print(temp.getData() + ",");
                temp = temp.getNext();
            }else{
                System.out.print(temp.getData() + "]");
                temp = temp.getNext();
            }
        }
    }

    /**
     * Devuelve el nodo en la posición especificada por el índice.
     *
     * @param i El índice del nodo a devolver.
     * @return El nodo en la posición especificada por el índice.
     */
    public NodoDoble<T> obtenerNodoPorIndice(int i){
        if(i<0){
            return null;
        }
        NodoDoble<T> temp=head;
        while (temp != null) {
            if(i==0) {
                return temp;
            }
            if(temp.getNext()!=null) {
                i--;
                temp = temp.getNext();
            }
        }
        return null;
    }

    /**
     * Devuelve la posición del nodo que contiene el valor especificado.
     *
     * @param data El valor a buscar en la lista.
     * @return La posición del nodo que contiene el valor especificado, o -1 si el valor no se encuentra en la lista.
     */
    public int getNodePosition(T data) {
        NodoDoble<T> current = head;
        int position = 1;
        while (current != null) {
            if (current.getData().equals(data)) {
                return position;
            }
            current = current.next;
            position++;
        }
        return -1; // node not found
    }
}

