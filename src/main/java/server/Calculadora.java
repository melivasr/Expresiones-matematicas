package server;

import org.json.JSONObject;
import server.modelo.Cliente;
import utils.ExpresionTree.ExpressionTree;


/**
 * Clase que representa el juego y se encarga de gestionar las conexiones de los clientes, el estado del juego y la lógica del juego.
 */
public class Calculadora {

    public static double EfectuarOperacion(JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        switch (jsonObject.getString("comando")) {
            case "evaluarNombre":
                if (jsonObject.has("nombre")) {
                    String nombre = jsonObject.getString("nombre");
                    double resultado = evaluarNombre(nombre);
                    System.out.println("Resultado: " + resultado);
                    return resultado;
                }
                break;
            default:
                System.err.println("Comando no encontrado");
        }
        return 0;
    }

    public static double evaluarNombre(String nombre) {
        ExpressionTree expressionTree = new ExpressionTree(nombre);
        double resultado = expressionTree.evaluate();
        return resultado;
    }

}
