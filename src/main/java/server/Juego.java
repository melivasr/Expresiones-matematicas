package server;

import org.json.JSONObject;
import server.modelo.Cliente;
import utils.Doble.ListaDoble;
import utils.Doble.NodoDoble;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * Clase que representa el juego y se encarga de gestionar las conexiones de los clientes, el estado del juego y la lógica del juego.
 */
public class Juego  implements Runnable {

    ConcurrentHashMap<Integer, Cliente> conexiones;

    ListaDoble<Cliente> listaCliente;


    NodoDoble<Cliente> clienteTurnoActual;


    private boolean ejecutandoJuego = true;

    /**
     * Constructor de la clase Juego.
     * Inicializa el juego con una matriz vacía y un estado en espera.
     */
    public Juego() {
        this.conexiones = new ConcurrentHashMap<>();
        this.listaCliente = new ListaDoble<>();
    }

    /**
     * Inicia el juego y establece el turno del primer cliente.
     */
    public void iniciar() {
        this.listaCliente.getTail().setNext(this.listaCliente.getHead());
        this.clienteTurnoActual = this.listaCliente.getHead();
        this.enviarMensajeATodos(Comandos.GetComandoServerIniciado(clienteTurnoActual.getData().getId(), clienteTurnoActual.getData().getNombre()));
    }


    /**
     * Envía un mensaje a todos los clientes conectados al juego.
     *
     * @param jsonString El mensaje a enviar en formato JSON.
     */
    public void enviarMensajeATodos(String jsonString) {
        Enumeration<Integer> llaves = this.conexiones.keys();
        while (llaves.hasMoreElements()) {
            int llave = llaves.nextElement();
            if (this.conexiones.containsKey(llave)) {
                this.conexiones.get(llave).getConnection().Enviar_mensaje(jsonString);
            }
        }
    }

    /**
     * Ejecuta el hilo que se encarga de gestionar las conexiones de los clientes y la lógica del juego.
     */
    @Override
    public void run() {
        while (ejecutandoJuego) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Enumeration<Integer> llaves = this.conexiones.keys();
            while (llaves.hasMoreElements()) {
                Integer llave = llaves.nextElement();
                Cliente cliente = this.conexiones.get(llave);
                if (cliente.getConnection().Revisar_bandeja()) {
                    String mensaje = cliente.getConnection().Obtener_mensaje();
                    JSONObject jsonObject = new JSONObject(mensaje);
                    System.out.println(mensaje);
                    this.revisarComandos(jsonObject, cliente);
                }
            }
        }
        System.out.println("juego finalizado");
    }

    private void revisarComandos(JSONObject jsonObject, Cliente cliente) {
        System.out.println(jsonObject.toString());
        switch (jsonObject.getString("comando")) {
            case "ponerLinea":
                break;
            default:
                System.err.println("Comando no encontrado");

        }
    }

}
