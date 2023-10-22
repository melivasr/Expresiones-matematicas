package client;

import client.interfaz.Window1Controller;
import client.socket.ServerConnection;
import javafx.application.Platform;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Clase que representa la lógica del juego y la comunicación con el servidor.
 * Implementa Runnable para permitir la ejecución concurrente.
 */
public class Juego{

    private static Juego instance;

    private boolean juegoIniciado;

    private ServerConnection server;

    private int idTurnoActual;

    private boolean leyendoMensajes = true;

    private Window1Controller window1Controller;

    private Juego ()
    {
        this.juegoIniciado = false;
        this.idTurnoActual = -1;
    }

    /**
     * Obtiene la instancia única de la clase Juego (patrón Singleton).
     *
     * @return La instancia única de la clase Juego.
     */
    public static Juego GetInstance()
    {

        if(instance == null)
        {
            instance = new Juego();
        }

        return instance;
    }
    /**
     * Reinicia la instancia única de la clase Juego a una nueva instancia.
     */
    public static void ResetInstance()
    {
        instance = new Juego();
    }


    /**
     * Establece el controlador del juego.
     *
     * @param gameController El controlador del juego.
     */

    /**
     * Establece el controlador de espera.
     *
     * @param window1Controller El controlador de espera.
     */
    public void setEsperaController(Window1Controller window1Controller) {
        this.window1Controller = window1Controller;
    }

    /**
     * Obtiene la conexión al servidor.
     *
     * @return La conexión al servidor.
     */
    public ServerConnection GetConnection()
    {
        return server;
    }

    /**
     * Realiza la conexión al servidor y establece los flujos de entrada y salida de datos.
     *
     * @param comando   El nombre del usuario
     * @return Verdadero si la conexión se realizó con éxito, falso en caso contrario.
     */
    public boolean Conectarse(String comando)
    {
        Socket misocket = null;
        try {
            misocket = new Socket("127.0.0.1", 9999);
            ServerConnection cliente = new ServerConnection(misocket);
            cliente.Enviar_mensaje(comando);
            System.out.println(comando);
            String entrada = cliente.LeerEntrada();
            System.out.println(entrada);
            mostrarResultado(new JSONObject(entrada));

            return true;

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    private void mostrarResultado(JSONObject jsonObject) {
        String resultado = jsonObject.getString("result");
            Platform.runLater(() -> {
                this.window1Controller.setResultadoLabel(resultado);
            });

    }

}