package client;

import client.interfaz.Window1Controller;
import client.socket.ServerConnection;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

/**
 * Clase que representa la lógica del juego y la comunicación con el servidor.
 * Implementa Runnable para permitir la ejecución concurrente.
 */
public class Juego{

    private static Juego instance;

    private ServerConnection server;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    private String nombreUsuario;

    private Juego ()
    {
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
    public String EjecutarComando(String comando)
    {
        Socket misocket = null;
        try {
            misocket = new Socket("127.0.0.1", 9999);
            ServerConnection cliente = new ServerConnection(misocket);
            cliente.Enviar_mensaje(comando);
            System.out.println(comando);
            String entrada = cliente.LeerEntrada();
            System.out.println(entrada);
            return new JSONObject(entrada).getString("result");

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}