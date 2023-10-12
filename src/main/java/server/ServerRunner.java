package server;

import server.socket.Recepcion;

/**
 * Clase que se encarga de iniciar el servidor y ejecutar el juego.
 * Inicia un hilo para el juego y otro para la recepción de conexiones.
 * Finalmente, ejecuta el inicializador del juego.
 */
public class ServerRunner {
    public static void main(String[] args) {
        Juego juego = new Juego();
        Recepcion recepcion=new Recepcion();
        Inicializador inicializador = new Inicializador(recepcion, juego);

        new Thread(juego).start();
        new Thread(recepcion).start();
    }
}
