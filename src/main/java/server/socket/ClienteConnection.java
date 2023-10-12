package server.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Clase encargada de gestionar las conexiones entre un cliente y el servidor.
 */
public class ClienteConnection implements Runnable {

    // Información del usuario conectado (nombre de usuario e IP)
    String nick, ip;

    //Mensaje a enviar.
    public String mensaje;

    public boolean conectado;

    //Flujo de salida de datos para enviar información al servidor.
    private DataOutputStream envioDatos;

    //Flujo de entrada de datos para recibir información del servidor.
    private DataInputStream entradaDatos;

    // Socket para la comunicación con el servidor.
    Socket socket;

    //Cola para almacenar los mensajes recibidos del servidor.
    public ConcurrentLinkedQueue<String> mensajes_recibidos;

    /**
     * Constructor de la clase ClienteConnection.
     * Inicializa la conexión con el servidor y guarda la información del usuario.
     *
     * @param nick Nombre de usuario.
     * @param ip Dirección IP del usuario.
     * @param socket Socket para la comunicación con el servidor.
     */
    public ClienteConnection(String nick, String ip, Socket socket){

        this.nick = nick;

        this.ip = ip;

        this.socket = socket;

        this.mensajes_recibidos= new ConcurrentLinkedQueue<>();

        this.conectado = true;

    }

    /**
     * Obtiene el flujo de entrada de datos del socket.
     * Si el flujo no se ha inicializado, lo crea.
     *
     * @return Flujo de entrada de datos del socket.
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
     * Si el flujo no se ha inicializado, lo crea.
     *
     * @return Flujo de salida de datos del socket.
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
     * Inicializa la conexión con el servidor utilizando un socket dado.
     *
     * @param misocket Socket para la comunicación con el servidor.
     */
    public ClienteConnection(Socket misocket) {
        this.socket= misocket;
    }

    /**
     * Envía un mensaje al servidor a través del flujo de salida de datos.
     *
     * @param mensaje Mensaje a enviar.
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
     * Obtiene y elimina el primer mensaje de la cola de mensajes recibidos.
     *
     * @return El primer mensaje de la cola de mensajes recibidos.
     */
    public String Obtener_mensaje(){

        return this.mensajes_recibidos.poll();
    }

    /**
     * Verifica si hay mensajes en la cola de mensajes recibidos.
     *
     * @return Verdadero si hay mensajes en la cola, falso en caso contrario.
     */
    public Boolean Revisar_bandeja(){
        return this.mensajes_recibidos != null && !this.mensajes_recibidos.isEmpty();


    }

    /**
     * Establece el nombre de usuario.
     *
     * @param nick El nuevo nombre de usuario.
     */
    public void setNick(String nick){
        this.nick= nick;
    }
    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getNick() {
        return nick;
    }

    public String LeerEntrada() {
        DataInputStream entradaDatos = this.getEntradaDatos();
        try {
            return entradaDatos.readUTF();
        } catch (IOException e) {
            System.out.println("Entrada exception");
        }
        return null;
    }

    /**
     * Inicia la ejecución del hilo que recibe mensajes del servidor.
     */
    @Override
    public void run() {
        System.out.println("Se inicio el juego");

        try {
            while(true){
                String message = this.LeerEntrada();

                if(this.mensajes_recibidos==null){
                    this.mensajes_recibidos= new ConcurrentLinkedQueue<>();
                }
                System.out.println(message);

                this.mensajes_recibidos.offer(message);
            }

        } catch (Exception e) {
            this.conectado = false;
            System.out.println("Salio");
        }

    }
}