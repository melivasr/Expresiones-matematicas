package server;

import server.modelo.Cliente;
import server.socket.ClienteConnection;
import server.socket.Recepcion;
import java.time.Duration;
import java.time.Instant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Clase que se encarga de inicializar el juego y ejecutarlo.
 */
public class Inicializador{

    private Recepcion recepcion;

    private Juego juego;

    /**
     * Constructor de la clase Inicializador.
     *
     * @param recepcion La instancia de la clase Recepcion.
     * @param juego La instancia de la clase Juego.
     */
    public Inicializador(Recepcion recepcion, Juego juego) {
        this.recepcion = recepcion;
        this.juego = juego;
    }

    /**
     * Inicializa el juego agregando los clientes a la cola de clientes del juego.
     */
    private void Inicializar()
    {

        juego.iniciar();
    }
    /**
     * Ejecuta el juego.
     */

}
