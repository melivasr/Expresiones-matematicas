package client.modelo;

/**
 * Clase Registro que representa un registro de una operación de cálculo.
 * Cada registro contiene una expresión, su resultado y la fecha de la operación.
 */
public class Registro {
    private String expresion;
    private String resultado;
    private String fecha;

    /**
     * Constructor de la clase Registro.
     * @param expresion la expresión matemática realizada.
     * @param resultado el resultado de la expresión matemática.
     * @param fecha la fecha en la que se realizó la operación.
     */
    public Registro(String expresion, String resultado, String fecha) {
        this.expresion = expresion;
        this.resultado = resultado;
        this.fecha = fecha;
    }
    /**
     * Obtiene la expresión matemática de este registro.
     * @return la expresión matemática.
     */
    public String getExpresion() {
        return expresion;
    }
    /**
     * Obtiene el resultado de la expresión matemática de este registro.
     * @return el resultado de la expresión matemática.
     */
    public String getResultado() {
        return resultado;
    }
    /**
     * Obtiene la fecha de la operación de este registro.
     * @return la fecha de la operación.
     */
    public String getFecha() {
        return fecha;
    }
}

