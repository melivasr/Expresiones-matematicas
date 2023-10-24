package client;

import client.socket.ServerConnection;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Clase que representa la lógica de la conexión con el servidor.
 * Implementa el patrón Singleton para garantizar una única instancia de la clase.
 *
 * @author Melissa Vásquez
 * @version 1.0
 */
public class Socket {

    private static Socket instance;

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombreUsuario El nombre del usuario a establecer.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    private String nombreUsuario;

    /**
     * Constructor privado para la implementación del patrón Singleton.
     */
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
     * Envía un comando al servidor y devuelve la respuesta.
     *
     * @param comando   El comando a enviar al servidor.
     * @return La respuesta del servidor al comando enviado.
     * @throws IOException Si ocurre un error durante la conexión con el servidor.
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