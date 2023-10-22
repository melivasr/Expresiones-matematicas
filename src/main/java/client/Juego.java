package client;

import client.Model.Usuario;
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
public class Juego implements Runnable{

    private static Juego instance;

    private boolean juegoIniciado;

    private ServerConnection server;

    private Usuario usuario;

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
     * @param name El nombre del usuario.
     * @return Verdadero si la conexión se realizó con éxito, falso en caso contrario.
     */
    public boolean Conectarse(String name, boolean isLogicalOperation)
    {
        Socket misocket = null;
        try {
            misocket = new Socket("127.0.0.1", 9999);
            ServerConnection cliente = new ServerConnection(misocket);
            String comando = isLogicalOperation ? Comandos.GetComandoLogicalOperation(name) : Comandos.GetComandoOperation(name);
            cliente.Enviar_mensaje(comando);
            System.out.println(comando);
            String entrada = cliente.LeerEntrada();
            System.out.println(entrada);
            JSONObject receivedJson = new JSONObject(entrada);
            int id = receivedJson.getInt("id");
            usuario = new Usuario(name, id);
            System.out.println("conectado");

            server = cliente;
            new Thread(cliente).start();
            new Thread(this).start();
            return true;

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el usuario que está jugando actualmente.
     *
     * @return El usuario que está jugando actualmente.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Ejecuta el hilo que recibe constantemente mensajes del servidor y realiza acciones en función de los comandos recibidos.
     */
    @Override
    public void run() {
        JSONObject receivedJson = null;
        try {
            while (leyendoMensajes) {
                TimeUnit.MILLISECONDS.sleep(500);
                if(this.GetConnection().Revisar_bandeja())
                {
                    String mensaje = this.GetConnection().Obtener_mensaje();
                    receivedJson = new JSONObject(mensaje);
                }
                if(receivedJson != null)
                {
                    this.revisarComandosPrevioJuego(receivedJson);
                }
                receivedJson = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Analiza los comandos recibidos del servidor antes del inicio del juego y realiza acciones en función de esos comandos.
     *
     * @param jsonObject El objeto JSON que contiene el comando recibido del servidor.
     */
    private void revisarComandosPrevioJuego(JSONObject jsonObject)

    {
        switch (jsonObject.getString("comando")) {
            case "serverIniciado":
                this.serverIniciado(jsonObject);
                break;
            case "resultadoCliente":
                mostrarResultado(jsonObject);
                break;
            default:
                System.err.println("Comando no encontrado");

        }

    }

    private void mostrarResultado(JSONObject jsonObject) {
        String resultado = jsonObject.getString("result");
            Platform.runLater(() -> {
                this.window1Controller.setResultadoLabel(resultado);
            });

    }

    /**
     * Maneja el comando "serverIniciado" del servidor, que indica el inicio del juego.
     *
     * @param jsonObject El objeto JSON que contiene los datos del servidor.
     */
    private void serverIniciado(JSONObject jsonObject)
    {
        //"{\"comando\":\"serverIniciado\",\"idTurnoActual\":\"%d\",\"nombreUsuario\":\"%s\"}"
        String nombre = jsonObject.getString("nombreUsuario");
        this.idTurnoActual = jsonObject.getInt("idTurnoActual");

        this.juegoIniciado = true;
    }

}