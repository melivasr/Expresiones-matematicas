package server.modelo;

import org.json.JSONObject;

import server.socket.ClienteConnection;

/**
 * Clase que representa un cliente conectado al servidor de la calculadora.
 * @author Melissa Vásquez
 */
public class Cliente {

    private ClienteConnection clienteConnection;
    private String nombre;
    private int id;


    /**
     * Constructor de la clase Cliente.
     *
     * @param clienteConnection La conexión del cliente con el servidor.
     * @param nombre            El nombre del cliente.
     * @param id                El identificador único del cliente.
     */
    public Cliente(ClienteConnection clienteConnection, String nombre, int id) {
        this.clienteConnection = clienteConnection;
        this.nombre = nombre;
        this.id = id;
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
}

