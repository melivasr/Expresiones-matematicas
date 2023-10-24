package server;

/**
 * Clase que se encarga de obtener los comandos
 */
public class Comandos {

/**
 * Método que genera el comando de resultado para el cliente.
 *
 * @param resultado El resultado de una operación de la calculadora```
 */
    public static String GetComandoResultado(String resultado)
    {

        return "{\"comando\":\"resultadoCliente\",\"result\":\"%s\"}".formatted(resultado.replace("\r", "\\r").replace("\n", "\\n"));
    }
}