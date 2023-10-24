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
public class ServerConnection{
    // Representa el nombre de usuario y la dirección IP del cliente
    String nick, ip;

    public String mensaje;
    // Flujo de salida para enviar datos al servidor
    private DataOutputStream envioDatos;

    // Flujo de entrada para recibir datos del servidor
    private DataInputStream entradaDatos;

    // Socket para la conexión con el servidor
    Socket socket;
    /**
     * Inicializa la conexión al servidor utilizando un socket dado.
     *
     * @param misocket Socket para la conexión con el servidor
     */
    public ServerConnection(Socket misocket) {
        this.socket = misocket;
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


}