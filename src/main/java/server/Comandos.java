package server;

/**
 * Clase que se encarga de obtener los comandos
 */
public class Comandos {

    public static String GetComandoResultado(String resultado)
    {

        return "{\"comando\":\"resultadoCliente\",\"result\":\"%s\"}".formatted(resultado.replace("\r", "\\r").replace("\n", "\\n"));
    }
}