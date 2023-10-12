package client;

import client.Model.Usuario;
import client.interfaz.EsperaController;
import client.socket.ServerConnection;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Optional;
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

    private EsperaController esperaController;

    private Juego ()
    {
        this.juegoIniciado = false;
        this.idTurnoActual = -1;
    }

    /**
     * Comprueba si el juego está en curso.
     *
     * @return Verdadero si el juego está en curso, falso en caso contrario.
     */
    public boolean estaJuegoIniciado()
    {
        return this.juegoIniciado;
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
     * Comprueba si es el turno del usuario actual.
     *
     * @return Verdadero si es el turno del usuario actual, falso en caso contrario.
     */
    public boolean esMiTurno()
    {
        return this.usuario.getId() == this.idTurnoActual;
    }

    /**
     * Establece el controlador del juego.
     *
     * @param gameController El controlador del juego.
     */

    /**
     * Establece el controlador de espera.
     *
     * @param esperaController El controlador de espera.
     */
    public void setEsperaController(EsperaController esperaController) {
        this.esperaController = esperaController;
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
    public boolean Conectarse(String name)
    {
        Socket misocket = null;
        try {
            misocket = new Socket("127.0.0.1", 9999);
            ServerConnection cliente = new ServerConnection(misocket);
            cliente.Enviar_mensaje(Comandos.GetComandoConexion(name));
            System.out.println(Comandos.GetComandoConexion(name));
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
            case "actualizarInformacion":
                this.actualizarInformacion(jsonObject);
                break;
            default:
                System.err.println("Comando no encontrado");

        }

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

    /**
     * Actualiza la información del juego en la interfaz de usuario en función de los datos recibidos del servidor.
     *
     * @param jsonObject El objeto JSON que contiene la información del juego.
     */
    private void actualizarInformacion(JSONObject jsonObject)
    {

        int numeroLinea = jsonObject.getInt("numeroLinea");
        String tipo = jsonObject.getString("tipo");
        String nombreActual = jsonObject.getString("nombreActual");
        String estado = jsonObject.getString("estado");
        String sigJugador = jsonObject.getString("nombreSigJugador");
        int sigJugadorId = jsonObject.getInt("idSigJugador");
        JSONArray cuadros = jsonObject.getJSONArray("cuadros");
        this.idTurnoActual = sigJugadorId;

        Platform.runLater(() -> {
            System.out.println("Actualizando informacion");

            Iterator<Object> iteratorObj = cuadros.iterator();
            while (iteratorObj.hasNext()) {
                Object next = iteratorObj.next();
                try {

                    int cuadro = Integer.parseInt(next.toString());

                } catch (NumberFormatException e) {
                    System.out.println(next);
                }

            }
        });

        if(estado.equals("finalizado"))
        {
            Juego.ResetInstance();
        }
    }
}