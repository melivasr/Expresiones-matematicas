package utils.Cola;

/**
 * Clase que implementa una cola genérica para almacenar elementos de tipo T.
 */
public class Cola<T> {
    // Representa el último elemento de la cola
    private Nodo<T> rear;

    // Representa el primer elemento de la cola
    private Nodo<T> front;

    // Representa la cantidad de elementos de la cola
    private int size;

    /**
     * Verifica si la cola está vacía.
     *
     * @return Verdadero si la cola está vacía, falso en caso contrario.
     */
    public boolean isEmpty() {
        boolean vacio=false;
        if(size==0) {
            vacio=true;
        }
        return vacio;
    }

    /**
     * Constructor de la clase Cola.
     * Inicializa la cola vacía.
     */
    public Cola(){
        front=null;
        rear=null;
        size=0;
    }

    /**
     * Añade un nuevo elemento al final de la cola.
     *
     * @param element El elemento a añadir a la cola.
     */
    public void enqueue(T element){
        Nodo<T> nodoNuevo =new Nodo<T>(element);
        if(front==null){
            rear= nodoNuevo;
            front= nodoNuevo;
            size++;
        }else{
            rear.setNext(nodoNuevo);
            rear= nodoNuevo;
            size++;
        }
    }

    /**
     * Elimina el primer elemento de la cola y lo devuelve.
     *
     * @return El primer elemento de la cola.
     */
    public Nodo<T> dequeue(){
        Nodo<T> nodoEliminado = null;
        if(front!=null){
            if(front.getNext()!=null){
                nodoEliminado =new Nodo<T>(front.getData());
                front=front.getNext();
                size--;
            }else{
                nodoEliminado =new Nodo<T>(front.getData());
                front=null;
                rear=null;
                size--;
            }
        }
        return nodoEliminado;
    }

    /**
     * Obtiene el primer elemento de la cola sin eliminarlo.
     *
     * @return El primer elemento de la cola.
     */
    public Nodo<T> peek(){
        Nodo<T> primerNodo = null;
        if(!isEmpty()){
            primerNodo = new Nodo<T>(front.getData());
        }
        return primerNodo;
    }

    /**
     * Obtiene la cantidad de elementos de la cola.
     *
     * @return La cantidad de elementos de la cola.
     */
    public int getSize() {
        return size;
    }
}