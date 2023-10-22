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
    public static String GetComandoOperation(String expression)
    {
        return "{\"comando\":\"evaluarExpression\",\"expression\":\"%s\"}".formatted(expression);
    }

    public static String GetComandoLogicalOperation(String expression)
    {
        return "{\"comando\":\"evaluarExpressionLogica\",\"expression\":\"%s\"}".formatted(expression);
    }


}
