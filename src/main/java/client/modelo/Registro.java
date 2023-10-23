package client.modelo;

public class Registro {
    private String expresion;
    private String resultado;
    private String fecha;

    public Registro(String expresion, String resultado, String fecha) {
        this.expresion = expresion;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public String getExpresion() {
        return expresion;
    }

    public String getResultado() {
        return resultado;
    }

    public String getFecha() {
        return fecha;
    }
}

