package server.socket;

import org.json.JSONObject;
import server.Calculadora;
import server.Comandos;
import server.modelo.Cliente;
import utils.Cola.Cola;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Clase de servidor que se encarga de recibir los mensajes de los clientes y reenviarlos a los demás.
 */
public class Recepcion implements Runnable {


    public int cantidadUsuariosConectados;

    public Cola<Cliente> clienteCola;

    /**
     * Constructor de la clase Recepcion.
     * Inicializa la cola de clientes.
     */
    public Recepcion(){
        this.clienteCola = new Cola<>();
    }

    /**
     * Inicia la escucha del servidor y acepta conexiones de clientes.
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub

        try {
            ServerSocket servidor= new ServerSocket(9999);


            System.out.println("Usando el puerto: " + servidor.getLocalPort());

            while(true){
                Socket misocket = servidor.accept();

                ClienteConnection conexion=new ClienteConnection("nick", "ip", misocket);
                int usuarioId = this.cantidadUsuariosConectados;
                this.cantidadUsuariosConectados ++;
                String mensajeMetadata = conexion.LeerEntrada();
                JSONObject jsonObject = new JSONObject(mensajeMetadata);
                System.out.println(mensajeMetadata);
                conexion.Enviar_mensaje(Comandos.GetComandoConexion(usuarioId));
                System.out.println(Comandos.GetComandoConexion(usuarioId));
                String resultado = Calculadora.EfectuarOperacion(jsonObject);
                String comandoResultado = Comandos.GetComandoResultado(resultado);
                conexion.Enviar_mensaje(comandoResultado);
                System.out.println(comandoResultado);

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}