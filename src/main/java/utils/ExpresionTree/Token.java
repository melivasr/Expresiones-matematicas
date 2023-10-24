package utils.ExpresionTree;

/**
 * Esta clase representa un token en la expresión.
 * Cada token tiene un valor de tipo String y un tipo de token.
 */
public class Token {
    String data;

    TokenType type;

    /**
     * Constructor de la clase Token.
     * @param data El valor del token.
     * @param type El tipo del token.
     */
    public Token(String data, TokenType type) {
        this.data = data;
        this.type = type;
    }

    /**
     * Método para agregar un carácter al valor del token.
     * @param c El carácter a agregar.
     */
    public void addChar(char c)
    {
        data += c;
    }

    /**
     * Método para verificar si el tipo del token es igual a un tipo específico.
     * @param type El tipo de token a comparar.
     * @return Verdadero si el tipo del token es igual al tipo especificado, falso en caso contrario.
     */
    public boolean isType(TokenType type)
    {
        return this.type == type;
    }

    /**
     * Método para obtener el valor del token.
     * @return El valor del token.
     */
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return data ;
    }
}

