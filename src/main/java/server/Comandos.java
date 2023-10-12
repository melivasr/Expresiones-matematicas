package server;

import server.modelo.Cliente;
import utils.Doble.ListaDoble;
import utils.Doble.NodoDoble;

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

    /**
     * Genera un comando para actualizar los cuadros en el juego.
     *
     * @param numeroLinea El número de línea.
     * @param tipo El tipo de línea.
     * @param usuarioActual El usuario actual.
     * @param cuadros Una lista con los cuadros.
     * @param siguienteJugadorId El identificador del siguiente jugador.
     * @param siguienteJugadorNombre El nombre del siguiente jugador.
     * @param juegoEstado El estado del juego.
     * @return Un String en formato JSON con el comando para actualizar los cuadros.
     */
    public static String GetComandoActualizarCuadros(int numeroLinea,
                                                     String tipo,
                                                     Cliente usuarioActual,
                                                     ListaDoble<Integer> cuadros,
                                                     int siguienteJugadorId,
                                                     String siguienteJugadorNombre,
                                                     String juegoEstado
                                                     )
    {
        return """
                {"comando":"actualizarInformacion",
                 "numeroLinea": "%d",
                 "tipo": "%s",
                 "nombreActual":"%s",
                 "cuadros": %s,
                 "estado": "%s",
                 "idSigJugador": "%d",
                 "nombreSigJugador": "%s",
                }
                """.formatted(numeroLinea,
                tipo,
                usuarioActual.getNombre(),
                GenerarCuadros(cuadros),
                juegoEstado,
                siguienteJugadorId,
                siguienteJugadorNombre

                );

    }

    /**
     * Genera un String en formato JSON con los cuadros.
     *
     * @param listaDoble Una lista doble con los cuadros.
     * @return Un String en formato JSON con los cuadros.
     */
    private static String GenerarCuadros(ListaDoble<Integer> listaDoble){
        StringBuilder jsonBuilder = new StringBuilder("[");
        NodoDoble<Integer> nodoActual = listaDoble.getHead();
        while(nodoActual != null)
        {
            jsonBuilder.append(nodoActual.getData());
            nodoActual = nodoActual.getNext();
            if(nodoActual != null)
            {
                jsonBuilder.append(", ");
            }

        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }

}
