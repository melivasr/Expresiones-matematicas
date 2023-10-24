package client;

/**
 * Clase que proporciona métodos para generar comandos en formato JSON.
 */
public class Comandos {

    /**
     * Genera un comando para evaluar una expresión matemática en formato JSON.
     *
     * @param expression La expresión matemática a evaluar.
     * @param nombre El nombre del usuario que envia el comando.
     * @return El comando de evaluación en formato JSON.
     */
    public static String GetComandoOperation(String expression, String nombre)
    {
        return "{\"comando\":\"evaluarExpression\",\"expression\":\"%s\",\"nombre\":\"%s\" }".formatted(expression, nombre);
    }

    /**
     * Genera un comando para evaluar una expresión lógica en formato JSON.
     *
     * @param expression La expresión lógica a evaluar.
     * @param nombre El nombre del usuario que envía el comando.
     * @return El comando de evaluación en formato JSON.
     */
    public static String GetComandoLogicalOperation(String expression, String nombre)
    {
        return "{\"comando\":\"evaluarExpressionLogica\",\"expression\":\"%s\",\"nombre\":\"%s\" }".formatted(expression, nombre);
    }

    /**
     * Genera un comando para solicitar una tabla de datos en formato JSON.
     *
     * @param nombre El nombre del usuario que envía el comando.
     * @return El comando de solicitud de tabla en formato JSON.
     */
    public static String GetComandoTableRequest(String nombre)
    {
        return "{\"comando\":\"tableRequest\",\"nombre\":\"%s\" }".formatted(nombre);
    }
}
