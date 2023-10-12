package client.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.*;

/**
 * Clase encargada de manejar la conexión entre clientes y el servidor.
 * Implementa Runnable para permitir la ejecución concurrente.
 */
public class ServerConnection implements Runnable {
    // Representa el nombre de usuario y la dirección IP del cliente
    String nick, ip;

    public String mensaje;
    // Flujo de salida para enviar datos al servidor
    private DataOutputStream envioDatos;

    private boolean ejecutandoHilo = true;

    // Flujo de entrada para recibir datos del servidor
    private DataInputStream entradaDatos;

    // Socket para la conexión con el servidor
    Socket socket;

    // Cola concurrente para almacenar los mensajes recibidos del servidor
    public ConcurrentLinkedQueue<String> mensajes_recibidos;

    /**
     * Constructor de la clase ServerConnection.
     * Inicializa el nombre de usuario, la dirección IP, el socket y la cola de mensajes recibidos.
     *
     * @param nick   Nombre de usuario del cliente
     * @param ip     Dirección IP del cliente
     * @param socket Socket para la conexión con el servidor
     */
    public ServerConnection(String nick, String ip, Socket socket) {

        this.nick = nick;

        this.ip = ip;

        this.socket = socket;

        this.mensajes_recibidos = new ConcurrentLinkedQueue<>();

    }
    /**
     * Finaliza la conexión y cierra el socket.
     */
    public void finalizarConexion() {
        try {
            this.ejecutandoHilo = false;
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Obtiene el flujo de entrada de datos del socket.
     * Si el flujo de entrada no está inicializado, lo inicializa.
     *
     * @return El flujo de entrada de datos del socket.
     */
    public DataInputStream getEntradaDatos() {
        if (this.entradaDatos == null) {
            try {
                this.entradaDatos = new DataInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return entradaDatos;
    }

    /**
     * Obtiene el flujo de salida de datos del socket.
     * Si el flujo de salida no está inicializado, lo inicializa.
     *
     * @return El flujo de salida de datos del socket.
     */
    public DataOutputStream getEnvioDatos() {
        if (this.envioDatos == null) {
            try {
                this.envioDatos = new DataOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return envioDatos;
    }

    /**
     * Inicializa la conexión al servidor utilizando un socket dado.
     *
     * @param misocket Socket para la conexión con el servidor
     */
    public ServerConnection(Socket misocket) {
        this.socket = misocket;
    }

    /**
     * Envia un mensaje al servidor.
     * El mensaje se escribe en el flujo de salida de datos y luego se vacía el flujo.
     *
     * @param mensaje El mensaje a enviar al servidor
     */
    public void Enviar_mensaje(String mensaje) {
        try {
            this.getEnvioDatos().writeUTF(mensaje);
            this.getEnvioDatos().flush();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println(e1.getMessage());
        }

    }
    /**
     * Obtiene el primer mensaje de la cola de mensajes recibidos.
     *
     * @return El primer mensaje de la cola de mensajes recibidos.
     */
    public String Obtener_mensaje() {

        return this.mensajes_recibidos.poll();
    }

    /**
     * Verifica si hay mensajes en la cola de mensajes recibidos.
     *
     * @return Verdadero si hay mensajes en la cola, falso en caso contrario.
     */
    public Boolean Revisar_bandeja() {
        return this.mensajes_recibidos != null && !this.mensajes_recibidos.isEmpty();


    }

    /**
     * Asigna un nuevo nombre de usuario.
     *
     * @param nick El nuevo nombre de usuario
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Obtiene el nombre de usuario actual.
     *
     * @return El nombre de usuario actual
     */
    public String getNick() {
        return nick;
    }

    /**
     * Lee datos del flujo de entrada del socket.
     *
     * @return Los datos leídos del flujo de entrada del socket.
     */
    public String LeerEntrada() {
        DataInputStream entradaDatos = this.getEntradaDatos();
        try {
            String entrada =  entradaDatos.readUTF();
            System.out.println("JSON "+ entrada);
            return entrada;
        } catch (Exception e) {
            System.out.println("Lectura desconectada");
        }
        return null;
    }

    /**
     * Ejecuta el hilo que recibe constantemente mensajes del servidor y los añade a la cola de mensajes recibidos.
     * Se ejecuta mientras la variable ejecutandoHilo sea verdadera.
     */

    @Override
    public void run() {
        System.out.println("Se inicio el juego");
        try {
            while (ejecutandoHilo) {
                String message = this.LeerEntrada();

                if (this.mensajes_recibidos == null) {
                    this.mensajes_recibidos = new ConcurrentLinkedQueue<>();
                }

                this.mensajes_recibidos.offer(message);
            }
        } catch (Exception e) {
            if(ejecutandoHilo) System.out.println("Desconectado");
        }
    }

}