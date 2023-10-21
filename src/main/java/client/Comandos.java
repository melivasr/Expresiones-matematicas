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
        return "{\"comando\":\"evaluarNombre\",\"nombre\":\"%s\"}".formatted(nombre);
    }

    public static String GetComandoResultado(double resultado)
    {
        return "{\"comando\":\"resultadoCliente\",\"result\":\"%s\"}".formatted(resultado);
    }


}
