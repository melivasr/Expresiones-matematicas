package client;

import client.Model.Usuario;

/**
 * Clase que proporciona métodos para generar comandos en formato JSON.
 */
public class Comandos {

    /**
     * Genera un comando de conexión en formato JSON.
     * Este comando indica al servidor que un usuario con el nombre proporcionado se está conectando.
     *
     * @param nombre El nombre del usuario que se está conectando.
     * @return El comando de conexión en formato JSON.
     */
    public static String GetComandoConexion(String nombre)
    {
        return "{\"comando\":\"conexion\",\"nombre\":\"%s\"}".formatted(nombre);
    }

    /**
     * Genera un comando para poner una línea en formato JSON.
     * Este comando indica al servidor que se está agregando una línea en una posición específica de un cierto tipo.
     *
     * @param numeroLinea El número de la línea que se está agregando.
     * @param tipo El tipo de línea que se está agregando.
     * @return El comando para poner una línea en formato JSON.
     */
    public static String GetComandoPonerLinea(int numeroLinea, String tipo)
    {
        return "{\"comando\":\"ponerLinea\",\"numeroLinea\":\"%d\",\"tipo\":\"%s\"}".formatted(numeroLinea, tipo);
    }

    /**
     * Genera un comando de estado de listas en formato JSON.
     *
     * @param usuario El objeto de usuario (posiblemente utilizado en futuras versiones).
     * @return El comando de estado de listas en formato JSON.
     */
    public static String GetComandoEstadoListas(Usuario usuario)
    {
        return "{\"comando\":\"estadoListas\"}";
    }


}
