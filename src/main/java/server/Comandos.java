package server;

/**
 * Clase que se encarga de obtener los comandos
 */
public class Comandos {

    /**
     * Genera un comando de conexión en formato JSON.
     *
     * @param usuarioId El identificador del usuario.
     * @return Un String en formato JSON con el comando de conexión.
     */
    public static String GetComandoConexion(int usuarioId)
    {
        return "{\"comando\":\"conexion\",\"id\":\"%d\"}".formatted(usuarioId);
    }

    /**
     * Genera un comando para indicar que el servidor ha iniciado.
     *
     * @param usuarioId El identificador del usuario.
     * @param nombreUsuario El nombre del usuario.
     * @return Un String en formato JSON con el comando para indicar que el servidor ha iniciado.
     */
    public static String GetComandoServerIniciado(int usuarioId, String nombreUsuario)
    {
        return "{\"comando\":\"serverIniciado\",\"idTurnoActual\":\"%d\",\"nombreUsuario\":\"%s\"}".formatted(usuarioId,nombreUsuario);
    }
    public static String GetComandoResultado(double resultado)
    {
        return "{\"comando\":\"resultadoCliente\",\"result\":\"%s\"}".formatted(resultado);
    }
}
