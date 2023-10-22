package client;

/**
 * Clase que proporciona métodos para generar comandos en formato JSON.
 */
public class Comandos {

    /**
     * Genera un comando de conexión en formato JSON.
     * Este comando indica al servidor que un usuario con el nombre proporcionado se está conectando.
     *
     * @param nombre El nombre del usuario que se está conectando.
     * @return El comando de conexión en formato JSON.
     */
    public static String GetComandoOperation(String expression, String nombre)
    {
        return "{\"comando\":\"evaluarExpression\",\"expression\":\"%s\",\"nombre\":\"%s\" }".formatted(expression, nombre);
    }

    public static String GetComandoLogicalOperation(String expression, String nombre)
    {
        return "{\"comando\":\"evaluarExpressionLogica\",\"expression\":\"%s\",\"nombre\":\"%s\" }".formatted(expression, nombre);
    }


    public static String GetComandoTableRequest(String nombre)
    {
        return "{\"comando\":\"tableRequest\",\"nombre\":\"%s\" }".formatted(nombre);
    }
}
