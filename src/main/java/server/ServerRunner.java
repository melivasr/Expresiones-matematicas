package server;

import server.socket.Recepcion;

/**
 * Clase que se encarga de iniciar el servidor y ejecutar la calculadora.
 */
public class ServerRunner {
    public static void main(String[] args) {
        Recepcion recepcion=new Recepcion();

        new Thread(recepcion).start();
    }
}
