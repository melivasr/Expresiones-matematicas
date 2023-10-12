package server.modelo;

import server.socket.ClienteConnection;

/**
 * Clase que representa un cliente conectado al servidor.
 */
public class Cliente {

    private ClienteConnection clienteConnection;
    private String nombre;
    private int id;

    /**
     * Constructor de la clase Cliente.
     *
     * @param clienteConnection La conexión del cliente con el servidor.
     * @param nombre El nombre del cliente.
     * @param id El identificador único del cliente.
     */
    public Cliente(ClienteConnection clienteConnection, String nombre, int id) {
        this.clienteConnection = clienteConnection;
        this.nombre = nombre;
        this.id = id;
    }
    /**
     * Obtiene la conexión del cliente.
     *
     * @return La conexión del cliente.
     */
    public ClienteConnection getConnection() {
        return clienteConnection;
    }

    /**
     * Comprueba si el cliente está conectado al servidor.
     *
     * @return Verdadero si el cliente está conectado al servidor, falso en caso contrario.
     */
    public boolean estaConectado()
    {
        return clienteConnection.conectado;
    }

    /**
     * Inicia un hilo independiente para escuchar mensajes del servidor a través de la conexión del cliente.
     */
    public void iniciarEscucha()
    {
        Thread mihilo= new Thread(this.getConnection());
        mihilo.start();
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return El identificador único del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param id El nuevo identificador único del cliente.
     */
    public void setId(int id) {
        this.id = id;
    }
}
