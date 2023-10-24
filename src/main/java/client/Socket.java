package client;

import client.socket.ServerConnection;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Clase que representa la lógica del juego y la comunicación con el servidor.
 * Implementa Runnable para permitir la ejecución concurrente.
 */
public class Socket {

    private static Socket instance;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    private String nombreUsuario;

    private Socket()
    {
    }

    /**
     * Obtiene la instancia única de la clase Socket (patrón Singleton).
     *
     * @return La instancia única de la clase Socket.
     */
    public static Socket GetInstance()
    {

        if(instance == null)
        {
            instance = new Socket();
        }

        return instance;
    }
    /**
     * Realiza la conexión al servidor y establece los flujos de entrada y salida de datos.
     *
     * @param comando   El nombre del usuario
     * @return Verdadero si la conexión se realizó con éxito, falso en caso contrario.
     */
    public String EjecutarComando(String comando)
    {
        java.net.Socket misocket = null;
        try {
            misocket = new java.net.Socket("127.0.0.1", 9999);
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