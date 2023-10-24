package utils.LogicalExpresionTree;

/**
 * Clase que representa un token lógico.
 * Un token lógico puede contener un valor de tipo String y un tipo de token.
 */
public class LogicalToken {
    String data;

    LogicalTokenType type;

    /**
     * Constructor de la clase LogicalToken.
     * @param data El valor del token lógico.
     * @param type El tipo del token lógico.
     */
    public LogicalToken(String data, LogicalTokenType type) {
        this.data = data;
        this.type = type;
    }

    /**
     * Método que añade un carácter al valor del token lógico.
     * @param c El carácter a añadir.
     */
    public void addChar(char c)
    {
        data += c;
    }

    /**
     * Método que verifica si el tipo del token lógico es igual a un tipo especificado.
     * @param type El tipo de token para comparar.
     * @return Verdadero si el tipo del token lógico es igual al tipo especificado, falso en caso contrario.
     */
    public boolean isType(LogicalTokenType type)
    {
        return this.type == type;
    }

    /**
     * Método que devuelve el valor del token lógico.
     * @return El valor del token lógico.
     */
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return data ;
    }
}







